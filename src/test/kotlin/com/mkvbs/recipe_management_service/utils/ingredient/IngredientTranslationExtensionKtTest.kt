package com.mkvbs.recipe_management_service.utils.ingredient

import com.mkvbs.recipe_management_service.domain.Locale
import com.mkvbs.recipe_management_service.domain.ingredient.IngredientTranslation
import com.mkvbs.recipe_management_service.exception.MissingDataException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.util.*

class IngredientTranslationExtensionKtTest {
    private val locale = Locale.en_US
    private val name = "egg"

    @Test
    fun `toResponseDto should return IngredientTranslationResponseDto with correct values`() {
        val id = UUID.randomUUID()
        val ingredientTranslation = IngredientTranslation(id = id, locale = locale, name = name)

        val result = ingredientTranslation.toResponseDto()

        assertEquals(id, result.id)
        assertEquals(locale, result.locale)
        assertEquals(name, result.name)
    }

    @Test
    fun `toResponseDto should throw MissingDataException when id is null`() {
        val ingredientTranslation = IngredientTranslation(locale = locale, name = name)

        val exception = assertThrows(MissingDataException::class.java) {
            ingredientTranslation.toResponseDto()
        }

        assertEquals("IngredientTranslation ID cannot be null", exception.message)
    }
}