package com.mkvbs.recipe_managment_service.service

import com.mkvbs.recipe_managment_service.domain.ingredient.Ingredient

interface IIngredientService {
    fun addIngredient(ingredientToSave: Ingredient): Ingredient
}