package com.mkvbs.recipe_management_service.utils.recipe

import com.mkvbs.recipe_management_service.domain.recipe.RecipeTranslation
import com.mkvbs.recipe_management_service.dto.recipe.RecipeTranslationResponseDto
import com.mkvbs.recipe_management_service.entity.recipe.RecipeEntity
import com.mkvbs.recipe_management_service.entity.recipe.RecipeTranslationEntity
import com.mkvbs.recipe_management_service.exception.MissingDataException

fun RecipeTranslation.toEntity(recipeEntity: RecipeEntity): RecipeTranslationEntity = RecipeTranslationEntity(
    id,
    locale,
    name,
    description,
    steps,
    recipeEntity
)

fun RecipeTranslation.toResponseDto(): RecipeTranslationResponseDto = RecipeTranslationResponseDto(
    id = id ?: throw MissingDataException("Ingredient ID cannot be null"),
    locale,
    name,
    description,
    steps
)
