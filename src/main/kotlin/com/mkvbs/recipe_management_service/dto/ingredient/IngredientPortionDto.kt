package com.mkvbs.recipe_management_service.dto.ingredient

import com.mkvbs.recipe_management_service.domain.ingredient.PortionType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(
    name = "Ingredient Portion",
    description = "Schema to hold basic information of a portion of an ingredient"
)
data class IngredientPortionDto(
    @field:Schema(description = "Type of the portion", example = "GRAM")
    val type: PortionType,

    @field:Schema(description = "Amount of calories in the portion", example = "250.0")
    val calories: Double,

    @field:Schema(description = "Amount of fat in grams", example = "10.5")
    val fat: Double,

    @field:Schema(description = "Amount of carbohydrates in grams", example = "30.0")
    val carbohydrates: Double,

    @field:Schema(description = "Amount of protein in grams", example = "15.0")
    val protein: Double,
)
