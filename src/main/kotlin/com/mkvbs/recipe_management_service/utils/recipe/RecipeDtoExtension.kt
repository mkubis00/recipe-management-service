package com.mkvbs.recipe_management_service.utils.recipe

import com.mkvbs.recipe_management_service.domain.ingredient.Ingredient
import com.mkvbs.recipe_management_service.domain.recipe.Recipe
import com.mkvbs.recipe_management_service.domain.recipe.RecipeIngredient
import com.mkvbs.recipe_management_service.dto.recipe.RecipeDto
import com.mkvbs.recipe_management_service.dto.recipe.RecipeIngredientDto
import com.mkvbs.recipe_management_service.exception.MissingDataException
import kotlin.time.Duration.Companion.minutes

fun RecipeDto.toDomain(existingIngredients: Set<Ingredient>): Recipe = Recipe.create(
    null,
    cookTime.minutes,
    tags.toMutableSet(),
    null,
    creatorId,
    mapIngredientsToRecipeIngredients(existingIngredients, ingredients),
    translations.map { it.toDomain() }
)

private fun mapIngredientsToRecipeIngredients(
    ingredients: Set<Ingredient>,
    recipeIngredients: List<RecipeIngredientDto>
): List<RecipeIngredient> {
    val recipeIngredientsToRecipe: List<RecipeIngredient> = mutableListOf()
    recipeIngredients.forEach { recipeIngredient ->
        val existingIngredient: Ingredient = ingredients.find { it.id == recipeIngredient.ingredientId }
            ?: throw MissingDataException("No ingredients found with id ${recipeIngredient.ingredientId}")
        recipeIngredient.toDomain(existingIngredient)
    }
    return recipeIngredientsToRecipe
}
