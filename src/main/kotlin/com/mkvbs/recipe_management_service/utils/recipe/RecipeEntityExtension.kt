package com.mkvbs.recipe_management_service.utils.recipe

import com.mkvbs.recipe_management_service.domain.recipe.Recipe
import com.mkvbs.recipe_management_service.entity.recipe.RecipeEntity

fun RecipeEntity.toDomain(): Recipe = Recipe.create(
    id,
    cookTime,
    tags,
    createdAt,
    creatorId,
    ingredients.map { it.toDomain() },
    translations.map { it.toDomain() }
)