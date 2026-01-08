package com.mkvbs.recipe_managment_service.utils.ingredient

import com.mkvbs.recipe_managment_service.domain.ingredient.IngredientTranslation
import com.mkvbs.recipe_managment_service.dto.ingredient.IngredientTranslationResponseDto
import com.mkvbs.recipe_managment_service.entity.ingredient.IngredientEntity
import com.mkvbs.recipe_managment_service.entity.ingredient.IngredientTranslationEntity
import com.mkvbs.recipe_managment_service.exception.MissingDataException

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