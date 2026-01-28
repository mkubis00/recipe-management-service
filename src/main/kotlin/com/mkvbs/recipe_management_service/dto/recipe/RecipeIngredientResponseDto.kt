package com.mkvbs.recipe_management_service.dto.recipe

import com.mkvbs.recipe_management_service.domain.ingredient.PortionType
import com.mkvbs.recipe_management_service.dto.ingredient.IngredientPortionResponseDto
import com.mkvbs.recipe_management_service.dto.ingredient.IngredientResponseDto
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(
    name = "Recipe Ingredient Response",
    description = "Schema to hold recipe's ingredient information"
)
data class RecipeIngredientResponseDto(
    @field:Schema(
        description = "UUID of the recipe ingredient",
        example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    )
    val id: UUID,

    @field:Schema(
        description = "Amount of the ingredient",
        example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    )
    val amount: Double,

    @field:Schema(
        description = "Portion type of the ingredient",
        example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    )
    val ingredientPortionType: PortionType,

    @field:Schema(
        description = "Description of the recipe information",
        implementation = IngredientPortionResponseDto::class
    )
    val ingredient: IngredientResponseDto,
)
