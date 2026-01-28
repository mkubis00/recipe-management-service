package com.mkvbs.recipe_management_service.utils.recipe

import com.mkvbs.recipe_management_service.domain.Locale
import com.mkvbs.recipe_management_service.domain.recipe.RecipeTranslation
import com.mkvbs.recipe_management_service.dummy_data.createFirstRecipeTranslationEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*

class RecipeTranslationEntityExtensionKtTest {

    @Test
    fun `toDomain should return RecipeTranslation with correct values`() {
        val recipeTranslationEntity = createFirstRecipeTranslationEntity()
        val result: RecipeTranslation = recipeTranslationEntity.toDomain()

        assertEquals(UUID.fromString("123e4567-e89b-12d3-a456-426655440000"), result.id)
        assertEquals(Locale.en_US, result.locale)
        assertEquals("Test Recipe", result.name)
    }
}