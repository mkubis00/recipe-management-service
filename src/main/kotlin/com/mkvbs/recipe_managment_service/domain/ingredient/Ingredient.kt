package com.mkvbs.recipe_managment_service.domain.ingredient

import java.util.*

class Ingredient(
    val id: UUID? = null,

    val translations: MutableSet<IngredientTranslation>,
    val portions: MutableSet<IngredientPortion>,
)