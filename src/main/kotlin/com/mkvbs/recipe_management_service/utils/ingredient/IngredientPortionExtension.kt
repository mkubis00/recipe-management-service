package com.mkvbs.recipe_management_service.utils.ingredient

import com.mkvbs.recipe_management_service.domain.ingredient.IngredientPortion
import com.mkvbs.recipe_management_service.dto.ingredient.IngredientPortionResponseDto
import com.mkvbs.recipe_management_service.entity.ingredient.IngredientEntity
import com.mkvbs.recipe_management_service.entity.ingredient.IngredientPortionEntity
import com.mkvbs.recipe_management_service.exception.MissingDataException

fun IngredientPortion.toResponseDto(): IngredientPortionResponseDto {
    return IngredientPortionResponseDto(
        id = id ?: throw MissingDataException("IngredientPortion ID cannot be null"),
        type,
        calories,
        fat,
        carbohydrates,
        protein
    )
}

fun IngredientPortion.toEntity(ingredientEntity: IngredientEntity): IngredientPortionEntity {
    return IngredientPortionEntity(id, type, calories, fat, carbohydrates, protein, ingredientEntity)
}