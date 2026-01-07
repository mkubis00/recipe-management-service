package com.mkvbs.recipe_managment_service.utlis.ingredient

import com.mkvbs.recipe_managment_service.domain.ingredient.IngredientTranslation
import com.mkvbs.recipe_managment_service.dto.ingredient.IngredientTranslationDto

fun IngredientTranslationDto.toDomain(): IngredientTranslation {
    return IngredientTranslation(null, locale, name)
}