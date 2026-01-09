package com.mkvbs.recipe_management_service.utils.ingredient

import com.mkvbs.recipe_management_service.domain.Locale
import com.mkvbs.recipe_management_service.domain.ingredient.Ingredient
import com.mkvbs.recipe_management_service.domain.ingredient.IngredientPortion
import com.mkvbs.recipe_management_service.domain.ingredient.IngredientTranslation
import com.mkvbs.recipe_management_service.domain.ingredient.PortionType
import com.mkvbs.recipe_management_service.exception.MissingDataException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.*


class IngredientExtensionKtTest {
    private val translation1Id = UUID.randomUUID()
    private val translation2Id = UUID.randomUUID()

    private val translation1 = IngredientTranslation(id = translation1Id, locale = Locale.en_US, name = "Sugar")
    private val translation2 = IngredientTranslation(id = translation2Id, locale = Locale.pl_PL, name = "Cukier")

    private val portion1Id = UUID.randomUUID()
    private val portion2Id = UUID.randomUUID()
    private val calories = 100.0
    private val fat = 5.0
    private val carbohydrates = 20.0
    private val protein = 10.0

    private val portion1 = IngredientPortion(
        id = portion1Id,
        type = PortionType.GRAM,
        calories = calories,
        fat = fat,
        carbohydrates = carbohydrates,
        protein = protein
    )
    val portion2 = IngredientPortion(
        id = portion2Id,
        type = PortionType.TEASPOON,
        calories = calories,
        fat = fat,
        carbohydrates = carbohydrates,
        protein = protein
    )

    @Test
    fun `toResponseDto should convert Ingredient to IngredientResponseDto when all fields are populated`() {
        val ingredientId = UUID.randomUUID()

        val ingredient = Ingredient.create(
            ingredientId,
            listOf(translation1, translation2),
            listOf(portion1, portion2)
        )

        val result = ingredient.toResponseDto()

        assertNotNull(result)
        assertEquals(ingredientId, result.id)
        assertEquals(2, result.translations.size)
        assertEquals(2, result.portions.size)
        assertTrue(result.translations.any { it.id == translation1.id && it.name == translation1.name })
        assertTrue(result.translations.any { it.id == translation2.id && it.name == translation2.name })
        assertTrue(result.portions.any { it.id == portion1.id })
        assertTrue(result.portions.any { it.id == portion2.id })
    }

    @Test
    fun `toResponseDto should throw MissingDataException when Ingredient ID is null`() {
        val ingredient = Ingredient.create(
            null,
            listOf(translation1, translation2),
            listOf(portion1, portion2)
        )

        val exception = assertThrows(MissingDataException::class.java) {
            ingredient.toResponseDto()
        }
        assertEquals("Ingredient ID cannot be null", exception.message)
    }
}