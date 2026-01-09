package com.mkvbs.recipe_management_service.dto.ingredient

import com.mkvbs.recipe_management_service.domain.Locale
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

@Schema(
    name = "Ingredient Translation",
    description = "Schema to hold ingredient translation information"
)
data class IngredientTranslationDto(

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


