package com.mkvbs.recipe_management_service.utils.ingredient

import com.mkvbs.recipe_management_service.domain.ingredient.IngredientTranslation
import com.mkvbs.recipe_management_service.dto.ingredient.IngredientTranslationDto

fun IngredientTranslationDto.toDomain(): IngredientTranslation {
    return IngredientTranslation(null, locale, name)
}