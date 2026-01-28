package com.mkvbs.recipe_management_service.entity.recipe

import com.mkvbs.recipe_management_service.domain.Locale
import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
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
import org.hibernate.annotations.BatchSize
import java.util.*

@Entity(name = "recipe_translation")
@Table(
    uniqueConstraints = [
        UniqueConstraint(
            name = "uq_locale_recipe_translation",
            columnNames = ["locale", "recipe_id"]
        )
    ]
)
@BatchSize(size = 10)
data class RecipeTranslationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val locale: Locale,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val description: String,

    @ElementCollection
    @CollectionTable(name = "recipe_step", joinColumns = [JoinColumn(name = "recipe_id")])
    @Column(name = "step", nullable = false)
    val steps: MutableSet<String>,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    val recipe: RecipeEntity,
)
