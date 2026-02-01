package com.mkvbs.recipe_management_service.utils.recipe

import com.mkvbs.recipe_management_service.domain.recipe.RecipeTranslation
import com.mkvbs.recipe_management_service.dto.recipe.RecipeTranslationDto

fun RecipeTranslationDto.toDomain(): RecipeTranslation = RecipeTranslation(
    null,
    locale,
    name,
    description,
    steps
)
