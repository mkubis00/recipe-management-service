package com.mkvbs.recipe_managment_service.utils.ingredient

import com.mkvbs.recipe_managment_service.domain.ingredient.IngredientPortion
import com.mkvbs.recipe_managment_service.entity.ingredient.IngredientPortionEntity
import com.mkvbs.recipe_managment_service.exception.MissingDataException

fun IngredientPortionEntity.toDomain(): IngredientPortion {
    return IngredientPortion(
        id = id ?: throw MissingDataException("ID of ingredient portion cannot be null"),
        type,
        calories,
        fat,
        carbohydrates,
        protein
    )
}