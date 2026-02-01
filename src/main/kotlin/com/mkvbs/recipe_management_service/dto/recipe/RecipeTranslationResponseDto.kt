package com.mkvbs.recipe_management_service.dto.recipe

import com.mkvbs.recipe_management_service.domain.Locale
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import java.util.*

@Schema(
    name = "Recipe Translation Response",
    description = "Schema to hold recipe translation information"
)
data class RecipeTranslationResponseDto(
    @field:Schema(
        description = "UUID of the recipe",
        example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    )
    val id: UUID,

    @field:Schema(
        description = "Locale of the recipe, representing the language and regional settings",
        example = "en_US"
    )
    val locale: Locale,

    @field:Schema(
        description = "Name of the recipe, only one ingredient with specific name can exists",
        example = "Scramble egg"
    )
    @field:NotEmpty(message = "Name can not be null or empty")
    @field:Size(min = 3, max = 30, message = "Name can not be less than 3 and not greater than 30 characters")
    val name: String,

    @field:NotEmpty(message = "Description can not be null or empty")
    @field:Size(min = 10, max = 200, message = "Name can not be less than 10 and not greater than 200 characters")
    val description: String,

    @field:Schema(
        description = "A list of unique process steps",
        example = "[\"START\", \"VALIDATE\", \"FINISH\"]"
    )
    val steps: MutableSet<String>,
)
