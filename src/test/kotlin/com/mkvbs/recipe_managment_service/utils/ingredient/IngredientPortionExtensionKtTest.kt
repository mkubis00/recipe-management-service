package com.mkvbs.recipe_managment_service.utils.ingredient

import com.mkvbs.recipe_managment_service.domain.ingredient.IngredientPortion
import com.mkvbs.recipe_managment_service.domain.ingredient.PortionType
import com.mkvbs.recipe_managment_service.entity.ingredient.IngredientEntity
import com.mkvbs.recipe_managment_service.exception.MissingDataException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.util.*

class IngredientPortionExtensionKtTest {

    val id = UUID.randomUUID()
    val type = PortionType.GRAM
    val calories = 100.0
    val fat = 5.0
    val carbohydrates = 20.0
    val protein = 10.0

    val ingredientPortion = IngredientPortion(
        id = id,
        type = type,
        calories = calories,
        fat = fat,
        carbohydrates = carbohydrates,
        protein = protein
    )

    @Test
    fun `toResponseDto should convert IngredientPortion to IngredientPortionResponseDto successfully`() {
        val responseDto = ingredientPortion.toResponseDto()

        assertEquals(id, responseDto.id)
        assertEquals(type, responseDto.type)
        assertEquals(calories, responseDto.calories)
        assertEquals(fat, responseDto.fat)
        assertEquals(carbohydrates, responseDto.carbohydrates)
        assertEquals(protein, responseDto.protein)
    }

    @Test
    fun `toResponseDto should throw MissingDataException when ID is null`() {
        val ingredientPortion = IngredientPortion(
            id = null,
            type = type,
            calories = calories,
            fat = fat,
            carbohydrates = carbohydrates,
            protein = protein
        )

        val exception = assertThrows(MissingDataException::class.java) {
            ingredientPortion.toResponseDto()
        }
        assertEquals("IngredientPortion ID cannot be null", exception.message)
    }

    @Test
    fun `toEntity should convert IngredientPortion to IngredientPortionEntity successfully`() {
        val ingredientEntity = IngredientEntity(
            id = UUID.randomUUID(),
            translations = mutableSetOf(),
            portions = mutableSetOf()
        )

        val portionEntity = ingredientPortion.toEntity(ingredientEntity)

        assertEquals(id, portionEntity.id)
        assertEquals(type, portionEntity.type)
        assertEquals(calories, portionEntity.calories)
        assertEquals(fat, portionEntity.fat)
        assertEquals(carbohydrates, portionEntity.carbohydrates)
        assertEquals(protein, portionEntity.protein)
        assertEquals(ingredientEntity, portionEntity.ingredient)
    }
}