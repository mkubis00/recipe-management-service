package com.mkvbs.recipe_managment_service.utils.ingredient

import com.mkvbs.recipe_managment_service.domain.ingredient.Ingredient
import com.mkvbs.recipe_managment_service.entity.ingredient.IngredientEntity

fun IngredientEntity.toDomain(): Ingredient {
    val translations = translations.map { it.toDomain() }
    val portions = portions.map { it.toDomain() }
    return Ingredient.fromEntity(id, translations, portions)
}
