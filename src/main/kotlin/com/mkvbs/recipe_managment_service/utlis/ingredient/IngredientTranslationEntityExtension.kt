package com.mkvbs.recipe_managment_service.utlis.ingredient

import com.mkvbs.recipe_managment_service.domain.ingredient.IngredientTranslation
import com.mkvbs.recipe_managment_service.entity.ingredient.IngredientTranslationEntity

fun IngredientTranslationEntity.toDomain(): IngredientTranslation {
    return IngredientTranslation(id, locale, name)
}