package com.mkvbs.recipe_management_service.utils.recipe

import com.mkvbs.recipe_management_service.domain.ingredient.Ingredient
import com.mkvbs.recipe_management_service.domain.recipe.RecipeIngredient
import com.mkvbs.recipe_management_service.dto.recipe.RecipeIngredientDto
import com.mkvbs.recipe_management_service.exception.FymException

fun RecipeIngredientDto.toDomain(ingredient: Ingredient): RecipeIngredient {
    if (ingredient.id != ingredientId) throw FymException("Wrong ingredient id: $ingredientId")
    return RecipeIngredient(
        null, amount, ingredientPortionType, ingredient
    )
}
