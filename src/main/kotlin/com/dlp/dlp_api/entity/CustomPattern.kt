package com.dlp.dlp_api.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "custom_patterns")
class CustomPattern(
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    var id: Long? = null,
    @Column(nullable = false, unique = true)
    var name: String,
    @Column(nullable = false)
    var regex: String,
    @Column(nullable = false)
    var score: Float,

    @ManyToOne
    @JoinColumn(name = "entity_type_id")
    @JsonIgnore
    var entityType: CustomEntityType

)