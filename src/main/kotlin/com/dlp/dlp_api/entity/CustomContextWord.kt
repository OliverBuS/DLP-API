package com.dlp.dlp_api.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "custom_context_words")
class CustomContextWord (
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    var id: Long? = null,
    @Column(nullable = false)
    var word: String,

    @ManyToOne
    @JoinColumn(name="entity_type_id")
    @JsonIgnore
    var entityType: CustomEntityType,
)
