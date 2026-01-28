package com.mkvbs.recipe_management_service.domain.recipe

import com.mkvbs.recipe_management_service.domain.ingredient.Ingredient
import com.mkvbs.recipe_management_service.domain.ingredient.PortionType
import java.util.*

class RecipeIngredient(
    val id: UUID?,
    val amount: Double,
    val ingredientPortionType: PortionType,
    val ingredient: Ingredient,
)