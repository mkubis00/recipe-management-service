package com.mkvbs.recipe_managment_service.entity.ingredient

import com.mkvbs.recipe_managment_service.domain.Locale
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.util.*

@Entity(name = "ingredient_translation")
@Table(
    uniqueConstraints = [
        UniqueConstraint(
            name = "uq_locale_ingredient_translation",
            columnNames = ["locale", "ingredient_id"]
        )
    ]
)
class IngredientTranslationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val locale: Locale,

    @Column(nullable = false)
    val name: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    val ingredient: IngredientEntity,
)