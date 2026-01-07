package com.mkvbs.recipe_managment_service.service

import com.mkvbs.recipe_managment_service.domain.ingredient.Ingredient
import com.mkvbs.recipe_managment_service.domain.ingredient.IngredientTranslation
import com.mkvbs.recipe_managment_service.exception.ResourceAlreadyExistsException
import com.mkvbs.recipe_managment_service.repository.IngredientRepository
import com.mkvbs.recipe_managment_service.repository.IngredientTranslationRepository
import com.mkvbs.recipe_managment_service.utlis.ingredient.toDomain
import com.mkvbs.recipe_managment_service.utlis.ingredient.toEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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