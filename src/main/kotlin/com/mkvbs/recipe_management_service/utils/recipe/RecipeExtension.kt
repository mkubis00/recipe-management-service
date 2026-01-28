package com.mkvbs.recipe_management_service.utils.recipe

import com.mkvbs.recipe_management_service.domain.recipe.Recipe
import com.mkvbs.recipe_management_service.dto.recipe.RecipeResponseDto
import com.mkvbs.recipe_management_service.entity.recipe.RecipeEntity
import com.mkvbs.recipe_management_service.exception.MissingDataException
import kotlin.time.DurationUnit

fun Recipe.toEntity(): RecipeEntity {
    val recipeEntity = RecipeEntity(
        id,
        cookTime,
        tags,
        createdAt,
        creatorId,
        mutableSetOf(),
        mutableSetOf(),
    )

    val ingredients = ingredients.map { it.toEntity(recipeEntity) }
    val translations = translations.map { it.toEntity(recipeEntity) }

    recipeEntity.ingredients.addAll(ingredients)
    recipeEntity.translations.addAll(translations)

    return recipeEntity
}

fun Recipe.toResponseDto(): RecipeResponseDto = RecipeResponseDto(
    id = id ?: throw MissingDataException("Recipe ID cannot be null"),
    cookTime.toInt(DurationUnit.MINUTES),
    tags.toList(),
    createdAt = createdAt ?: throw MissingDataException("Recipe creation date cannot be null"),
    creatorId,
    translations.map { it.toResponseDto() },
    ingredients.map { it.toResponseDto() },
)
