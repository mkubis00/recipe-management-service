package com.mkvbs.recipe_managment_service.entity.ingredient

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import org.hibernate.annotations.BatchSize
import java.util.*

@BatchSize(size = 10)
@Entity(name = "ingredient")
class IngredientEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,

    @OneToMany(
        mappedBy = "ingredient",
        cascade = [CascadeType.PERSIST, CascadeType.REMOVE],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @BatchSize(size = 5)
    val translations: MutableSet<IngredientTranslationEntity>,

    @OneToMany(
        mappedBy = "ingredient",
        cascade = [CascadeType.PERSIST, CascadeType.REMOVE],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    @BatchSize(size = 10)
    val portions: MutableSet<IngredientPortionEntity>,
)
