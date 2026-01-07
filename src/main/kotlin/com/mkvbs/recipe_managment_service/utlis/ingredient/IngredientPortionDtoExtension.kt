package com.mkvbs.recipe_managment_service.utlis.ingredient

import com.mkvbs.recipe_managment_service.domain.ingredient.IngredientPortion
import com.mkvbs.recipe_managment_service.dto.ingredient.IngredientPortionDto

fun IngredientPortionDto.toDomain(): IngredientPortion {
    return IngredientPortion(null, type, calories, fat, carbohydrates, protein)
}