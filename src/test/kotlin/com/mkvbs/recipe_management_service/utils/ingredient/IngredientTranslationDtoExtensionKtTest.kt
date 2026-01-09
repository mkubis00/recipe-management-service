package com.mkvbs.recipe_management_service.utils.ingredient

import com.mkvbs.recipe_management_service.domain.Locale
import com.mkvbs.recipe_management_service.dto.ingredient.IngredientTranslationDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class IngredientTranslationDtoExtensionKtTest {
    @Test
    fun `toDomain should correctly map valid DTO to domain`() {
        // Arrange
        val dto = IngredientTranslationDto(locale = Locale.en_US, name = "Sugar")

        // Act
        val result = dto.toDomain()

        // Assert
        assertEquals(null, result.id)
        assertEquals(dto.locale, result.locale)
        assertEquals(dto.name, result.name)
    }
}