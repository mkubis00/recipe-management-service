package com.mkvbs.recipe_management_service.utils.ingredient

import com.mkvbs.recipe_management_service.domain.ingredient.IngredientTranslation
import com.mkvbs.recipe_management_service.entity.ingredient.IngredientTranslationEntity
import com.mkvbs.recipe_management_service.exception.MissingDataException

fun IngredientTranslationEntity.toDomain(): IngredientTranslation {
    return IngredientTranslation(
        id = id ?: throw MissingDataException("ID of ingredient translation cannot be null"),
        locale,
        name
    )
}