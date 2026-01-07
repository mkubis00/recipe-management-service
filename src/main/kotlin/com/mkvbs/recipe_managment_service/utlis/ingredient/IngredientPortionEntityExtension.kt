package com.mkvbs.recipe_managment_service.utlis.ingredient

import com.mkvbs.recipe_managment_service.domain.ingredient.IngredientPortion
import com.mkvbs.recipe_managment_service.entity.ingredient.IngredientPortionEntity

fun IngredientPortionEntity.toDomain(): IngredientPortion {
    return IngredientPortion(id, type, calories, fat, carbohydrates, protein)
}