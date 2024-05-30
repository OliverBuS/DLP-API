package com.dlp.dlp_api.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "custom_entity_types")
class CustomEntityType (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "name", unique = true, nullable = false)
    val name: String,

    @Column(name = "description", nullable = false)
    val description: String = "",

    @Enumerated(EnumType.STRING)
    @Column(name = "detection_type", nullable = false)
    val detectionType: DetectionType,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "created_by")
    val createdBy: Int? = null,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_by")
    val updatedBy: Int? = null,

    @OneToMany(mappedBy = "entityType", cascade = [CascadeType.ALL], orphanRemoval = true)
    val denyList: List<CustomDenyList> = emptyList(),

    @OneToMany(mappedBy = "entityType", cascade = [CascadeType.ALL], orphanRemoval = true)
    val patterns: List<CustomPattern> = emptyList(),

    @OneToMany(mappedBy = "entityType", cascade = [CascadeType.ALL], orphanRemoval = true)
    val contextWords: List<CustomContextWord> = emptyList()

)

enum class DetectionType {
    Dictionary, Pattern, Tag
}