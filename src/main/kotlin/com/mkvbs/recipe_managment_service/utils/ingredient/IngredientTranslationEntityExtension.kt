package com.mkvbs.recipe_managment_service.utils.ingredient

import com.mkvbs.recipe_managment_service.domain.ingredient.IngredientTranslation
import com.mkvbs.recipe_managment_service.entity.ingredient.IngredientTranslationEntity
import com.mkvbs.recipe_managment_service.exception.MissingDataException

fun IngredientTranslationEntity.toDomain(): IngredientTranslation {
    return IngredientTranslation(
        id = id ?: throw MissingDataException("ID of ingredient translation cannot be null"),
        locale,
        name
    )
}