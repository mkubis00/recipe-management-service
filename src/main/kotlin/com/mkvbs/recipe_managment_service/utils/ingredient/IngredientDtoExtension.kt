package com.mkvbs.recipe_managment_service.utils.ingredient

import com.mkvbs.recipe_managment_service.domain.ingredient.Ingredient
import com.mkvbs.recipe_managment_service.dto.ingredient.IngredientDto

fun IngredientDto.toDomain(): Ingredient {
    val translations = translations.map { it.toDomain() }.toMutableSet()
    val portions = portions.map { it.toDomain() }.toMutableSet()
    return Ingredient(null, translations, portions)
}