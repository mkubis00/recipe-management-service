package com.mkvbs.recipe_managment_service.domain.ingredient

import com.mkvbs.recipe_managment_service.domain.Locale
import java.util.*

class IngredientTranslation(
    val id: UUID? = null,
    val locale: Locale,
    val name: String,
)