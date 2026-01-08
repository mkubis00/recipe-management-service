package com.mkvbs.recipe_managment_service.dto.ingredient

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid

@Schema(
    name = "Ingredient",
    description = "Schema to hold ingredient information"
)
data class IngredientDto(
    @field:Valid
    @field:ArraySchema(
        schema = Schema(implementation = IngredientTranslationDto::class),
        minItems = 1
    )
    val translations: MutableSet<IngredientTranslationDto>,

    @field:Valid
    @field:ArraySchema(
        schema = Schema(implementation = IngredientPortionDto::class),
        minItems = 1
    )
    val portions: MutableSet<IngredientPortionDto>,
)