package com.mkvbs.recipe_management_service.domain

/**
 * Represents a language and geographical or cultural localization setting.
 * Typically used to specify localized resources, translations, or formatting preferences.
 *
 * @property code A string representation of the locale, typically in the format
 * "language_COUNTRY" (e.g., "en_US" for English - United States, "pl_PL" for Polish - Poland).
 */
enum class Locale(val code: String) {
    pl_PL("pl_PL"),
    en_US("en_US")
}
