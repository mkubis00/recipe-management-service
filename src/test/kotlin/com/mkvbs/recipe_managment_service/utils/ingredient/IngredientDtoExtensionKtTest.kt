package com.mkvbs.recipe_managment_service.utils.ingredient

import com.mkvbs.recipe_managment_service.domain.Locale
import com.mkvbs.recipe_managment_service.domain.ingredient.PortionType
import com.mkvbs.recipe_managment_service.dto.ingredient.IngredientDto
import com.mkvbs.recipe_managment_service.dto.ingredient.IngredientPortionDto
import com.mkvbs.recipe_managment_service.dto.ingredient.IngredientTranslationDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class IngredientDtoExtensionKtTest {
    private val enLocale = Locale.en_US
    private val enName = "Sugar"
    private val portion1 = PortionType.KILOGRAM
    private val calories1 = 10.0
    private val fat1 = 10.1
    private val carbohydrates1 = 10.2
    private val protein1 = 10.3

    private val plLocale = Locale.pl_PL
    private val plName = "Cukier"
    private val portion2 = PortionType.TEASPOON
    private val calories2 = 20.0
    private val fat2 = 20.1
    private val carbohydrates2 = 20.2
    private val protein2 = 20.3

    private val translationDto1 = IngredientTranslationDto(enLocale, enName)
    private val translationDto2 = IngredientTranslationDto(plLocale, plName)
    private val portionDto1 = IngredientPortionDto(portion1, calories1, fat1, carbohydrates1, protein1)
    private val portionDto2 = IngredientPortionDto(portion2, calories2, fat2, carbohydrates2, protein2)

    private val ingredientDto = IngredientDto(
        mutableSetOf(translationDto1, translationDto2),
        mutableSetOf(portionDto1, portionDto2)
    )

    @Test
    fun `toDomain should map IngredientDto with valid data to Ingredient`() {
        val domainIngredient = ingredientDto.toDomain()

        assertEquals(null, domainIngredient.id)
        assertEquals(2, domainIngredient.translations.size)
        assertEquals(2, domainIngredient.portions.size)
        assertEquals(enName, domainIngredient.translations.first().name)
        assertEquals(plName, domainIngredient.translations.last().name)
    }
}