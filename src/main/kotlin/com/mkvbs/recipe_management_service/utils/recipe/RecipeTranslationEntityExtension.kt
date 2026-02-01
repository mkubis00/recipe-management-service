package com.mkvbs.recipe_management_service.utils.recipe

import com.mkvbs.recipe_management_service.domain.recipe.RecipeTranslation
import com.mkvbs.recipe_management_service.entity.recipe.RecipeTranslationEntity

fun RecipeTranslationEntity.toDomain() = RecipeTranslation(
    id, locale, name, description, steps
)