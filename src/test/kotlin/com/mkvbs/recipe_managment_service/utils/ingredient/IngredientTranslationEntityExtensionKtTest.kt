package com.mkvbs.recipe_managment_service.utils.ingredient

import com.mkvbs.recipe_managment_service.domain.Locale
import com.mkvbs.recipe_managment_service.entity.ingredient.IngredientEntity
import com.mkvbs.recipe_managment_service.entity.ingredient.IngredientTranslationEntity
import com.mkvbs.recipe_managment_service.exception.MissingDataException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*
import kotlin.test.assertEquals

class IngredientTranslationEntityExtensionKtTest {

    @Test
    fun `should correctly map all fields from IngredientTranslationEntity to IngredientTranslation`() {
        val id = UUID.randomUUID()
        val locale = Locale.en_US
        val name = "Sugar"
        val ingredientEntity =
            IngredientEntity(UUID.randomUUID(), mutableSetOf(), mutableSetOf())

        val ingredientTranslationEntity = IngredientTranslationEntity(
            id = id,
            locale = locale,
            name = name,
            ingredient = ingredientEntity
        )

        // When
        val result = ingredientTranslationEntity.toDomain()

        // Then
        assertEquals(id, result.id)
        assertEquals(locale, result.locale)
        assertEquals(name, result.name)
    }

    @Test
    fun `should handle null id in IngredientTranslationEntity and map to IngredientTranslation`() {
        // Given
        val locale = Locale.en_US
        val name = "Sugar"
        val ingredientEntity = IngredientEntity(UUID.randomUUID(), mutableSetOf(), mutableSetOf())

        val ingredientTranslationEntity = IngredientTranslationEntity(
            id = null,
            locale = locale,
            name = name,
            ingredient = ingredientEntity
        )

        val exception = assertThrows<MissingDataException> {
            ingredientTranslationEntity.toDomain()
        }

        assertEquals("ID of ingredient translation cannot be null", exception.message)
    }
}