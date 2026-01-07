package com.mkvbs.recipe_managment_service.domain.ingredient

import java.util.*

/**
 * Represents a specific portion of an ingredient, detailing its nutritional composition
 * and measurement type. A portion is used to calculate nutritional values for the ingredient
 * within the context of a recipe.
 *
 * @property id The unique identifier of the portion, or null if not initialized.
 * @property type The measurement type of the portion, represented as a `PortionType` enum.
 * @property calories The calorie content of the portion expressed in kilocalories.
 * @property fat The fat content of the portion in grams.
 * @property carbohydrates The carbohydrate content of the portion in grams.
 * @property protein The protein content of the portion in grams.
 */
data class IngredientPortion(
    val id: UUID?,
    val type: PortionType,
    val calories: Double,
    val fat: Double,
    val carbohydrates: Double,
    val protein: Double,
)