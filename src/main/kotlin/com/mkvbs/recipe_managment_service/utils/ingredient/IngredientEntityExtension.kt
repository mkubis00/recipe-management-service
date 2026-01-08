package com.mkvbs.recipe_managment_service.utils.ingredient

import com.mkvbs.recipe_managment_service.domain.ingredient.Ingredient
import com.mkvbs.recipe_managment_service.entity.ingredient.IngredientEntity
import com.mkvbs.recipe_managment_service.exception.MissingDataException

fun IngredientEntity.toDomain(): Ingredient {
    val translations = translations.map { it.toDomain() }.toMutableSet()
    val portions = portions.map { it.toDomain() }.toMutableSet()
    return Ingredient(
        id = id ?: throw MissingDataException("ID of ingredient cannot be null"),
        translations,
        portions
    )
}