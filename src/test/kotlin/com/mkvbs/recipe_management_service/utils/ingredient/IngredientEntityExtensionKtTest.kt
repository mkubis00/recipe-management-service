package com.mkvbs.recipe_management_service.utils.ingredient

import com.mkvbs.recipe_management_service.domain.Locale
import com.mkvbs.recipe_management_service.domain.ingredient.PortionType
import com.mkvbs.recipe_management_service.entity.ingredient.IngredientEntity
import com.mkvbs.recipe_management_service.entity.ingredient.IngredientPortionEntity
import com.mkvbs.recipe_management_service.entity.ingredient.IngredientTranslationEntity
import com.mkvbs.recipe_management_service.exception.MissingDataException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertThrows
import java.util.*


class IngredientEntityExtensionKtTest {

    private val translationEntity1 = IngredientTranslationEntity(
        id = UUID.randomUUID(),
        locale = Locale.en_US,
        name = "Sugar",
        ingredient = IngredientEntity(null, mutableSetOf(), mutableSetOf())
    )
    private val translationEntity2 = IngredientTranslationEntity(
        id = UUID.randomUUID(),
        locale = Locale.pl_PL,
        name = "Cukier",
        ingredient = IngredientEntity(null, mutableSetOf(), mutableSetOf())
    )

    private val portionEntity1 = IngredientPortionEntity(
        id = UUID.randomUUID(),
        type = PortionType.GRAM,
        calories = 100.0,
        fat = 0.0,
        carbohydrates = 25.0,
        protein = 0.0,
        ingredient = IngredientEntity(null, mutableSetOf(), mutableSetOf())
    )
    private val portionEntity2 = IngredientPortionEntity(
        id = UUID.randomUUID(),
        type = PortionType.TEASPOON,
        calories = 16.0,
        fat = 0.0,
        carbohydrates = 4.0,
        protein = 0.0,
        ingredient = IngredientEntity(null, mutableSetOf(), mutableSetOf())
    )

    private val translations = mutableSetOf(translationEntity1, translationEntity2)
    private val portions = mutableSetOf(portionEntity1, portionEntity2)

    @Test
    fun `toDomain should correctly map IngredientEntity to Ingredient`() {
        val ingredientId = UUID.randomUUID()

        val ingredientEntity = IngredientEntity(
            id = ingredientId,
            translations,
            portions
        )

        val result = ingredientEntity.toDomain()

        assertNotNull(result)
        assertEquals(ingredientId, result.id)
        assertEquals(2, result.translations.size)
        assertEquals(2, result.portions.size)
    }

    @Test
    fun `toDomain should throw MissingDataException when IngredientEntity ID is null`() {
        val ingredientEntity = IngredientEntity(
            id = null,
            translations,
            portions
        )

        val exception = assertThrows<MissingDataException> {
            ingredientEntity.toDomain()
        }

        assertEquals("ID of ingredient cannot be null", exception.message)
    }
}