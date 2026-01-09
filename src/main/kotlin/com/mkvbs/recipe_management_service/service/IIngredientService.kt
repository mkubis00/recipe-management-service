package com.mkvbs.recipe_management_service.service

import com.mkvbs.recipe_management_service.domain.Locale
import com.mkvbs.recipe_management_service.domain.ingredient.Ingredient
import java.util.*

interface IIngredientService {
    fun addIngredient(ingredientToSave: Ingredient): Ingredient

    fun deleteIngredient(id: UUID)

    fun getIngredientById(id: UUID): Ingredient

    fun getIngredientByName(name: String, locale: Locale): Ingredient
}