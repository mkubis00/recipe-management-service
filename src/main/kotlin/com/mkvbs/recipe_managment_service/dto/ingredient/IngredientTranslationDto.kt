package com.mkvbs.recipe_managment_service.dto.ingredient

import com.mkvbs.recipe_managment_service.domain.Locale
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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IngredientTranslationDto

        return locale == other.locale
    }

    override fun hashCode(): Int {
        return locale.hashCode()
    }
}

