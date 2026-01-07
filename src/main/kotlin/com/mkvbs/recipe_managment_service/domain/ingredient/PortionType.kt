package com.mkvbs.recipe_managment_service.domain.ingredient

/**
 * Enum representing the types of measurement units that can be used to define ingredient portions in a recipe.
 *
 * This enumeration is utilized to categorize the measurement type of a portion, facilitating
 * accurate nutritional calculations and standardized representation of ingredient quantities.
 *
 * Enum values:
 * - GRAM: Represents a measurement in grams.
 * - KILOGRAM: Represents a measurement in kilograms.
 * - MILLILITER: Represents a measurement in milliliters.
 * - LITER: Represents a measurement in liters.
 * - CUP: Represents a measurement as a cup (standardized to a particular volume).
 * - SPOON: Represents a measurement as a spoon, typically a tablespoon.
 * - TEASPOON: Represents a measurement as a teaspoon.
 * - PIECE: Represents an individual or whole item (e.g., one apple).
 * - SLICE: Represents a sliced segment of an ingredient (e.g., one slice of bread).
 * - PINCH: Represents a small amount, typically used for spices or seasonings.
 */
enum class PortionType {
    GRAM,
    KILOGRAM,
    MILLILITER,
    LITER,
    CUP,
    SPOON,
    TEASPOON,
    PIECE,
    SLICE,
    PINCH,
}