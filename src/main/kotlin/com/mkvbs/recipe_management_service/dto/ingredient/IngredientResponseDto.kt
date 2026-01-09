package com.mkvbs.recipe_management_service.dto.ingredient

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(
    name = "Ingredient Response",
    description = "Schema to hold ingredient information"
)
data class IngredientResponseDto(
    @field:Schema(
        description = "UUID of the ingredient",
        example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    )
    val id: UUID,

    @field:ArraySchema(
        schema = Schema(implementation = IngredientTranslationResponseDto::class),
        minItems = 1
    )
    val translations: List<IngredientTranslationResponseDto>,

    @field:ArraySchema(
        schema = Schema(implementation = IngredientPortionResponseDto::class),
        minItems = 1
    )
    val portions: List<IngredientPortionResponseDto>,
)