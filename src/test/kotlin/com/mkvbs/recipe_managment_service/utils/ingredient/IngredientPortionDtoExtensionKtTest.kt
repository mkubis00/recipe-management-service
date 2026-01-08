package com.mkvbs.recipe_managment_service.utils.ingredient

import com.mkvbs.recipe_managment_service.domain.ingredient.PortionType
import com.mkvbs.recipe_managment_service.dto.ingredient.IngredientPortionDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class IngredientPortionDtoExtensionKtTest {
    private val type = PortionType.GRAM
    private val calories = 250.0
    private val fat = 10.5
    private val carbohydrates = 30.0
    private val protein = 15.0
    private val portionDto = IngredientPortionDto(type, calories, fat, carbohydrates, protein)

    @Test
    fun `toDomain should map IngredientPortionDto with valid data to IngredientPortion`() {
        val portionDomain = portionDto.toDomain()

        assertEquals(null, portionDomain.id)
        assertEquals(type, portionDomain.type)
        assertEquals(calories, portionDomain.calories)
        assertEquals(fat, portionDomain.fat)
        assertEquals(carbohydrates, portionDomain.carbohydrates)
        assertEquals(protein, portionDomain.protein)
    }
}