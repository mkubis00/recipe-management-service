package com.mkvbs.recipe_management_service.domain.ingredient

import com.mkvbs.recipe_management_service.domain.Locale
import com.mkvbs.recipe_management_service.exception.FymException
import com.mkvbs.recipe_management_service.exception.MissingDataException
import java.util.*

class Ingredient private constructor(
    val id: UUID? = null,
) {
    private val _translations: MutableSet<IngredientTranslation> = mutableSetOf()
    private val _portions: MutableSet<IngredientPortion> = mutableSetOf()
    val translations: Set<IngredientTranslation> get() = _translations
    val portions: Set<IngredientPortion> get() = _portions

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
        _translations.add(newTranslation)
    }

    fun addPortion(newPortion: IngredientPortion) {
        validateLocaleUniqueness(newPortion.type)
        _portions.add(newPortion)
    }

    private fun validateLocaleUniqueness(type: PortionType) {
        if (_portions.any { it.type == type }) {
            throw FymException("Portion for type $type was given more then ones for this ingredient")
        }
    }

    private fun validateTranslationUniqueness(locale: Locale) {
        if (_translations.any { it.locale == locale }) {
            throw FymException("Translation for locale $locale was given more then ones for this ingredient")
        }
    }
}