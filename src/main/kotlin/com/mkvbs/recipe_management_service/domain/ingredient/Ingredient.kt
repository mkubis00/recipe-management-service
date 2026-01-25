package com.mkvbs.recipe_management_service.domain.ingredient

import com.mkvbs.recipe_management_service.domain.Locale
import com.mkvbs.recipe_management_service.exception.FymException
import com.mkvbs.recipe_management_service.exception.MissingDataException
import java.util.*

class Ingredient private constructor(
    val id: UUID? = null,
) {
    val translations: Set<IngredientTranslation>
        field: MutableSet<IngredientTranslation> = mutableSetOf()
    val portions: Set<IngredientPortion>
        field: MutableSet<IngredientPortion> = mutableSetOf()


    companion object {
        fun fromDto(translations: List<IngredientTranslation>, portions: List<IngredientPortion>): Ingredient {
            return Ingredient(null).apply {
                addTranslations(translations)
                addPortions(portions)
            }
        }

        fun fromEntity(
            id: UUID?,
            translations: List<IngredientTranslation>,
            portions: List<IngredientPortion>
        ): Ingredient {
            return Ingredient(id = id ?: throw MissingDataException("ID of ingredient cannot be null")).apply {
                addTranslations(translations)
                addPortions(portions)
            }
        }

        fun create(
            id: UUID?,
            translations: List<IngredientTranslation>,
            portions: List<IngredientPortion>
        ): Ingredient {
            return Ingredient(id).apply {
                addTranslations(translations)
                addPortions(portions)
            }
        }
    }

    fun addTranslations(newTranslations: List<IngredientTranslation>) {
        newTranslations.forEach(this::addTranslation)
    }

    fun addPortions(newPortions: List<IngredientPortion>) {
        newPortions.forEach(this::addPortion)
    }

    fun addTranslation(newTranslation: IngredientTranslation) {
        validateTranslationUniqueness(newTranslation.locale)
        translations.add(newTranslation)
    }

    fun addPortion(newPortion: IngredientPortion) {
        validateLocaleUniqueness(newPortion.type)
        portions.add(newPortion)
    }

    private fun validateLocaleUniqueness(type: PortionType) {
        if (portions.any { it.type == type }) {
            throw FymException("Portion for type $type was given more then ones for this ingredient")
        }
    }

    private fun validateTranslationUniqueness(locale: Locale) {
        if (translations.any { it.locale == locale }) {
            throw FymException("Translation for locale $locale was given more then ones for this ingredient")
        }
    }
}