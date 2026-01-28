package com.mkvbs.recipe_management_service.utils.recipe

import com.mkvbs.recipe_management_service.domain.recipe.RecipeIngredient
import com.mkvbs.recipe_management_service.dto.recipe.RecipeIngredientResponseDto
import com.mkvbs.recipe_management_service.entity.recipe.RecipeEntity
import com.mkvbs.recipe_management_service.entity.recipe.RecipeIngredientEntity
import com.mkvbs.recipe_management_service.exception.MissingDataException
import com.mkvbs.recipe_management_service.utils.ingredient.toEntity
import com.mkvbs.recipe_management_service.utils.ingredient.toResponseDto

fun RecipeIngredient.toEntity(recipeEntity: RecipeEntity): RecipeIngredientEntity = RecipeIngredientEntity(
    id,
    amount,
    ingredientPortionType,
    recipeEntity,
    ingredient.toEntity()
)

fun RecipeIngredient.toResponseDto(): RecipeIngredientResponseDto = RecipeIngredientResponseDto(
    id = id ?: throw MissingDataException("Ingredient ID cannot be null"),
    amount,
    ingredientPortionType,
    ingredient.toResponseDto()
)