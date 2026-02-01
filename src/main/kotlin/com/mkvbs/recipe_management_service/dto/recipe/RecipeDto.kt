package com.mkvbs.recipe_management_service.dto.recipe

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(
    name = "Recipe",
    description = "Schema to hold recipe information"
)
data class RecipeDto(
    @field:Schema(
        description = "Cooking time in minutes",
        example = "30"
    )
    val cookTime: Int,

    @field:Schema(
        description = "A list of unique process steps",
        example = "[\"TAG1\", \"TAG2\", \"TAG3\"]"
    )
    val tags: List<String>,

    @field:Schema(
        description = "UUID of the recipe creator",
        example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    )
    val creatorId: UUID,

    @field:ArraySchema(
        schema = Schema(implementation = RecipeTranslationDto::class),
        minItems = 1
    )
    val translations: List<RecipeTranslationDto>,

    @field:ArraySchema(
        schema = Schema(implementation = RecipeIngredientDto::class),
        minItems = 1
    )
    val ingredients: List<RecipeIngredientDto>,
)
