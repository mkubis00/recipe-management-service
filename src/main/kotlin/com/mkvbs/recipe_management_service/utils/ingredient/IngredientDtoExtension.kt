package com.mkvbs.recipe_management_service.utils.ingredient

import com.mkvbs.recipe_management_service.domain.ingredient.Ingredient
import com.mkvbs.recipe_management_service.dto.ingredient.IngredientDto

fun IngredientDto.toDomain(): Ingredient {
    val translations = translations.map { it.toDomain() }
    val portions = portions.map { it.toDomain() }
    return Ingredient.fromDto(translations, portions)
}