package com.mkvbs.recipe_management_service.utils.ingredient

import com.mkvbs.recipe_management_service.domain.ingredient.IngredientPortion
import com.mkvbs.recipe_management_service.entity.ingredient.IngredientPortionEntity
import com.mkvbs.recipe_management_service.exception.MissingDataException

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