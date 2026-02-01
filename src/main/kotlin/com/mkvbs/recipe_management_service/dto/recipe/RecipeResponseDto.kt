package com.mkvbs.recipe_management_service.dto.recipe

import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import java.time.Instant
import java.util.*

@Schema(
    name = "Recipe Response",
    description = "Schema to hold recipe information"
)
data class RecipeResponseDto(
    @field:Schema(
        description = "UUID of the recipe",
        example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    )
    val id: UUID,

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
        description = "Time when recipe is created",
        example = "1970-01-01T00:00:00Z"
    )
    val createdAt: Instant,

    @field:Schema(
        description = "UUID of the recipe creator",
        example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    )
    val creatorId: UUID,

    @field:ArraySchema(
        schema = Schema(implementation = RecipeTranslationResponseDto::class),
        minItems = 1
    )
    val translations: List<RecipeTranslationResponseDto>,

    @field:ArraySchema(
        schema = Schema(implementation = RecipeIngredientResponseDto::class),
        minItems = 3
    )
    val ingredients: List<RecipeIngredientResponseDto>,
)
