package com.mkvbs.recipe_management_service.utils.ingredient

import com.mkvbs.recipe_management_service.domain.ingredient.IngredientTranslation
import com.mkvbs.recipe_management_service.dto.ingredient.IngredientTranslationResponseDto
import com.mkvbs.recipe_management_service.entity.ingredient.IngredientEntity
import com.mkvbs.recipe_management_service.entity.ingredient.IngredientTranslationEntity
import com.mkvbs.recipe_management_service.exception.MissingDataException

fun IngredientTranslation.toResponseDto(): IngredientTranslationResponseDto {
    return IngredientTranslationResponseDto(
        id = id ?: throw MissingDataException("IngredientTranslation ID cannot be null"),
        locale,
        name
    )
}

fun IngredientTranslation.toEntity(ingredientEntity: IngredientEntity): IngredientTranslationEntity {
    return IngredientTranslationEntity(id, locale, name, ingredientEntity)
}