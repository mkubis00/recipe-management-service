package com.mkvbs.recipe_managment_service.service

import com.mkvbs.recipe_managment_service.domain.Locale
import com.mkvbs.recipe_managment_service.domain.ingredient.Ingredient
import com.mkvbs.recipe_managment_service.domain.ingredient.IngredientTranslation
import com.mkvbs.recipe_managment_service.exception.ResourceAlreadyExistsException
import com.mkvbs.recipe_managment_service.exception.ResourceNotFoundException
import com.mkvbs.recipe_managment_service.repository.IngredientRepository
import com.mkvbs.recipe_managment_service.repository.IngredientTranslationRepository
import com.mkvbs.recipe_managment_service.utlis.ingredient.toDomain
import com.mkvbs.recipe_managment_service.utlis.ingredient.toEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class IngredientServiceImpl(
    val ingredientRepository: IngredientRepository,
    val translationRepository: IngredientTranslationRepository
) : IIngredientService {

    @Transactional
    override fun addIngredient(ingredientToSave: Ingredient): Ingredient {
        checkIngredientsTranslations(ingredientToSave.translations)

        val ingredientEntityToSave = ingredientToSave.toEntity()
        return ingredientRepository.save(ingredientEntityToSave).toDomain()
    }

    @Transactional
    override fun deleteIngredient(id: UUID) {
        ingredientRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Ingredient with id $id does not exist") }
        ingredientRepository.deleteById(id)
    }

    override fun getIngredientById(id: UUID): Ingredient {
        val foundedIngredientEntity = ingredientRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Ingredient with id $id does not exist") }
        return foundedIngredientEntity.toDomain()
    }

    override fun getIngredientByName(
        name: String,
        locale: Locale
    ): Ingredient {
        val foundedIngredientTranslationEntity = translationRepository.findByNameAndLocale(name, locale)
            .orElseThrow { ResourceNotFoundException("Ingredient with name $name and locale $locale does not exist") }
        return foundedIngredientTranslationEntity.ingredient.toDomain()
    }

    private fun checkIngredientsTranslations(translations: MutableSet<IngredientTranslation>) {
        val existingTranslations = mutableListOf<IngredientTranslation>()
        translations.forEach {
            if (translationRepository.existsIngredientTranslationEntitiesByNameAndLocale(it.name, it.locale)) {
                existingTranslations.add(it)
            }
        }

        if (existingTranslations.isNotEmpty()) {
            throw ResourceAlreadyExistsException("Translations already exist: ${existingTranslations.joinToString("; ") { "name: ${it.name}, locale: ${it.locale}" }}")
        }
    }
}