package com.dlp.dlp_api.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "custom_entity_types")
class CustomEntityType(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "name", unique = true, nullable = false)
    var name: String,

    @Column(name = "description", nullable = false)
    var description: String = "",

    @Enumerated(EnumType.STRING)
    @Column(name = "detection_type", nullable = false)
    var detectionType: DetectionType,

    @Column(name = "created_at", nullable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "created_by", updatable = false)
    var createdBy: Long? = null,

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_by")
    var updatedBy: Int? = null,

    @OneToMany(mappedBy = "entityType", cascade = [CascadeType.ALL], orphanRemoval = true)
    var denyList: List<CustomDenyList> = emptyList(),

    @OneToMany(mappedBy = "entityType", cascade = [CascadeType.ALL], orphanRemoval = true)
    var patterns: List<CustomPattern> = emptyList(),

    @OneToMany(mappedBy = "entityType", cascade = [CascadeType.ALL], orphanRemoval = true)
    var contextWords: List<CustomContextWord> = emptyList()

) {
    enum class DetectionType {
        Dictionary, Pattern, Tag
    }
}