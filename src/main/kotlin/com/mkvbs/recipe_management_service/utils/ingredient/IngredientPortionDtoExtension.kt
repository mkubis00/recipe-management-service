package com.mkvbs.recipe_management_service.utils.ingredient

import com.mkvbs.recipe_management_service.domain.ingredient.IngredientPortion
import com.mkvbs.recipe_management_service.dto.ingredient.IngredientPortionDto

fun IngredientPortionDto.toDomain(): IngredientPortion {
    return IngredientPortion(null, type, calories, fat, carbohydrates, protein)
}