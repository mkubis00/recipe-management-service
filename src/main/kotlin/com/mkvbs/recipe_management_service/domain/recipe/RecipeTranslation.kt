package com.mkvbs.recipe_management_service.domain.recipe

import com.mkvbs.recipe_management_service.domain.Locale
import java.util.*

class RecipeTranslation(
    val id: UUID? = null,
    val locale: Locale,
    val name: String,
    val description: String,
    val steps: MutableSet<String>,
) {
}