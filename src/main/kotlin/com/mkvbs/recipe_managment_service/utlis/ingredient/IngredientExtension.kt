package com.mkvbs.recipe_managment_service.utlis.ingredient

import com.mkvbs.recipe_managment_service.domain.ingredient.Ingredient
import com.mkvbs.recipe_managment_service.dto.ingredient.IngredientResponseDto
import com.mkvbs.recipe_managment_service.entity.ingredient.IngredientEntity
import com.mkvbs.recipe_managment_service.exception.MissingDataException

fun Ingredient.toResponseDto(): IngredientResponseDto {
    val translations = translations.map { it.toResponseDto() }.toMutableSet()
    val portions = portions.map { it.toResponseDto() }.toMutableSet()
    return IngredientResponseDto(
        id = id ?: throw MissingDataException("Ingredient ID cannot be null"),
        translations,
        portions
    )
}

fun Ingredient.toEntity(): IngredientEntity {
    val ingredientEntity = IngredientEntity(id, mutableSetOf(), mutableSetOf())

    val translations = translations.map { it.toEntity(ingredientEntity) }.toMutableSet()
    ingredientEntity.translations.addAll(translations)

    val portions = portions.map { it.toEntity(ingredientEntity) }.toMutableSet()
    ingredientEntity.portions.addAll(portions)

    return ingredientEntity
}