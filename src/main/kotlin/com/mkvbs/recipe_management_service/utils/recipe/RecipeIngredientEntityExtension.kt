package com.mkvbs.recipe_management_service.utils.recipe

import com.mkvbs.recipe_management_service.domain.recipe.RecipeIngredient
import com.mkvbs.recipe_management_service.entity.recipe.RecipeIngredientEntity
import com.mkvbs.recipe_management_service.utils.ingredient.toDomain

fun RecipeIngredientEntity.toDomain(): RecipeIngredient = RecipeIngredient(
    id,
    amount,
    ingredientPortionType,
    ingredient.toDomain()
)