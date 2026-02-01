package com.mkvbs.recipe_management_service.dummy_data

import com.mkvbs.recipe_management_service.domain.Locale
import com.mkvbs.recipe_management_service.entity.recipe.RecipeEntity
import com.mkvbs.recipe_management_service.entity.recipe.RecipeTranslationEntity
import java.time.Instant
import java.util.*
import kotlin.time.Duration.Companion.minutes

fun createFirstRecipeTranslationEntity() = RecipeTranslationEntity(
    UUID.fromString("123e4567-e89b-12d3-a456-426655440000"),
    Locale.en_US,
    "Test Recipe",
    "Test Recipe Description",
    mutableSetOf(
        "Step 1",
        "Step 2"
    ),
    createRecipeEntity()
)

fun createSecondRecipeTranslationEntity() = RecipeTranslationEntity(
    UUID.fromString("321e4567-e89b-12d3-a456-426655440000"),
    Locale.pl_PL,
    "Przepis",
    "Opis przepisu",
    mutableSetOf(
        "Krok 1",
        "Krok 2"
    ),
    createRecipeEntity()
)

fun createRecipeEntity(): RecipeEntity = RecipeEntity(
    UUID.fromString("222e4567-e89b-12d3-a456-426655440111"),
    30.minutes,
    mutableSetOf("tag1", "tag2"),
    Instant.parse("2025-01-01T12:34:56Z"),
    UUID.fromString("122e4567-e89b-12d3-a456-426655440111"),
    mutableSetOf(),
    mutableSetOf()
)