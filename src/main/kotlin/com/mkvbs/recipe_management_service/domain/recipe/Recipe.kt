package com.mkvbs.recipe_management_service.domain.recipe

import com.mkvbs.recipe_management_service.domain.Locale
import com.mkvbs.recipe_management_service.exception.FymException
import com.mkvbs.recipe_management_service.exception.MissingDataException
import java.time.Instant
import java.util.*
import kotlin.time.Duration

class Recipe private constructor(
    val id: UUID?,
    val cookTime: Duration,
    val tags: MutableSet<String>,
    val createdAt: Instant? = Instant.now(),
    val creatorId: UUID,
) {
    val ingredients: Set<RecipeIngredient>
        field: MutableSet<RecipeIngredient> = mutableSetOf()
    val translations: Set<RecipeTranslation>
        field: MutableSet<RecipeTranslation> = mutableSetOf()

    companion object {
        fun create(
            id: UUID?,
            cookTime: Duration,
            tags: MutableSet<String>,
            createdAt: Instant?,
            creatorId: UUID,
            ingredients: List<RecipeIngredient>,
            translations: List<RecipeTranslation>
        ): Recipe = Recipe(
            id,
            cookTime,
            tags,
            createdAt,
            creatorId
        ).apply {
            addIngredients(ingredients)
            addTranslations(translations)
        }
    }

    fun addTranslations(newTranslations: List<RecipeTranslation>) {
        newTranslations.forEach(this::addTranslation)
    }

    fun addTranslation(newTranslation: RecipeTranslation) {
        validateTranslationUniqueness(newTranslation.locale, newTranslation.id)
        translations.add(newTranslation)
    }

    fun addIngredients(newIngredients: List<RecipeIngredient>) {
        newIngredients.forEach(this::addIngredient)
    }

    fun addIngredient(newIngredient: RecipeIngredient) {
        validateIngredient(newIngredient)
        if (newIngredient.id != null) {
            validateIngredientsUniqueness(newIngredient.id)
            ingredients.add(newIngredient)
        }
    }

    private fun validateTranslationUniqueness(locale: Locale, id: UUID?) {
        if (translations.any { it.locale == locale || it.id == id }) {
            throw FymException("Translation for locale $locale was given more then ones for this ingredient")
        }
    }

    private fun validateIngredient(newIngredient: RecipeIngredient) {
        if (newIngredient.amount > 0.0) {
            throw FymException("Ingredient amount must be > 0")
        }
        if (newIngredient.ingredient.id == null) {
            throw MissingDataException("Ingredient id cannot be null when adding ingredient to recipe")
        }
    }

    private fun validateIngredientsUniqueness(id: UUID) {
        if (ingredients.any { it.ingredient.id == id }) {
            throw FymException("Translation for ID $id was given more then ones for this ingredient")
        }
    }
}