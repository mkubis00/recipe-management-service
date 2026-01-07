package com.mkvbs.recipe_managment_service.dto.ingredient

import com.mkvbs.recipe_managment_service.domain.Locale
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import java.util.*

@Schema(
    name = "Ingredient Translation Response",
    description = "Schema to hold ingredient translation information"
)
data class IngredientTranslationResponseDto(
    @field:Schema(
        description = "UUID of the ingredient",
        example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    )
    val id: UUID,

    @field:Schema(
        description = "Locale of the ingredient, representing the language and regional settings",
        example = "en_US"
    )
    val locale: Locale,

    @field:Schema(
        description = "Name of the ingredient, only one ingredient with specific name can exists",
        example = "egg"
    )
    @field:NotEmpty(message = "Name can not be null or empty")
    @field:Size(min = 3, max = 30, message = "Name can not be less than 3 and not greater than 30 characters")
    val name: String,
)
