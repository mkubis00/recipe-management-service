package com.mkvbs.recipe_management_service.utils.ingredient

import com.mkvbs.recipe_management_service.domain.ingredient.PortionType
import com.mkvbs.recipe_management_service.entity.ingredient.IngredientEntity
import com.mkvbs.recipe_management_service.entity.ingredient.IngredientPortionEntity
import com.mkvbs.recipe_management_service.exception.MissingDataException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

/**
 * Unit tests for the `toDomain` function in `IngredientPortionEntityExtensionKt`.
 * This function maps an `IngredientPortionEntity` to an `IngredientPortion` domain model.
 */
class IngredientPortionEntityExtensionKtTest {
    private val id = UUID.randomUUID()
    private val type = PortionType.GRAM
    private val calories = 100.0
    private val fat = 5.0
    private val carbohydrates = 20.0
    private val protein = 10.0
    private val ingredientEntity = IngredientEntity(UUID.randomUUID(), mutableSetOf(), mutableSetOf())

    @Test
    fun `toDomain should map IngredientPortionEntity with all data correctly`() {
        val entity = IngredientPortionEntity(
            id = id,
            type = type,
            calories = calories,
            fat = fat,
            carbohydrates = carbohydrates,
            protein = protein,
            ingredient = ingredientEntity
        )

        val domain = entity.toDomain()

        assertEquals(id, domain.id)
        assertEquals(type, domain.type)
        assertEquals(calories, domain.calories)
        assertEquals(fat, domain.fat)
        assertEquals(carbohydrates, domain.carbohydrates)
        assertEquals(protein, domain.protein)
    }

    @Test
    fun `toDomain should map IngredientPortionEntity with null id`() {
        val entity = IngredientPortionEntity(
            id = null,
            type = type,
            calories = calories,
            fat = fat,
            carbohydrates = carbohydrates,
            protein = protein,
            ingredient = ingredientEntity
        )

        val exception = assertThrows<MissingDataException> {
            entity.toDomain()
        }

        kotlin.test.assertEquals("ID of ingredient portion cannot be null", exception.message)
    }
}