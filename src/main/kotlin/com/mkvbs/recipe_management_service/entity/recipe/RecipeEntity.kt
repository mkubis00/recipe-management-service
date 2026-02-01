package com.mkvbs.recipe_management_service.entity.recipe

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant
import java.util.*
import kotlin.time.Duration

@Entity(name = "recipe")
@BatchSize(size = 10)
data class RecipeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,

    @Column(nullable = false)
    val cookTime: Duration,

    val tags: MutableSet<String>,

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    val createdAt: Instant?,

    val creatorId: UUID,

    @OneToMany(
        mappedBy = "recipe",
        cascade = [CascadeType.PERSIST, CascadeType.REMOVE], // to check
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @BatchSize(size = 10)
    val ingredients: MutableSet<RecipeIngredientEntity>,

    @OneToMany(
        mappedBy = "recipe",
        cascade = [CascadeType.PERSIST, CascadeType.REMOVE],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @BatchSize(size = 5)
    val translations: MutableSet<RecipeTranslationEntity>,
)