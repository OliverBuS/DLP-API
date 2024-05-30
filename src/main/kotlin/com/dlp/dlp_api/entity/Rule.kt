package com.dlp.dlp_api.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "rules")
data class Rule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "codigo")
    val code: String? = null,

    @Column(name = "name", nullable = false)
    val name: String,

    @ManyToOne
    @JoinColumn(name = "entity_id", nullable = false)
    val entityType: CustomEntityType,

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false)
    val action: Action,

    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    val level: AlertLevel,

    @Column(name = "description", nullable = false)
    val description: String,

    @Column(name = "status", nullable = false)
    val status: Boolean = true,

    @Column(name = "confidence_level", nullable = false)
    val confidenceLevel: Double,

    @Column(name = "hits_lower", nullable = false)
    val hitsLower: Int,

    @Column(name = "hits_upper", nullable = false)
    val hitsUpper: Int,

    @Column(name = "alerts", nullable = false)
    val alerts: Boolean = false,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "created_by")
    val createdBy: Int? = null,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_by")
    val updatedBy: Int? = null,

    @ManyToMany
    @JoinTable(
        name = "groups_rules",
        joinColumns = [JoinColumn(name = "rule_id")],
        inverseJoinColumns = [JoinColumn(name = "network_id")]
    )
    val networks: Set<Network> = emptySet()
) {
    enum class Action {
        Alert,
        Redact,
        Block
    }

    enum class AlertLevel {
        High,
        Medium,
        Low
    }

    companion object {
        fun generateRandomUUID(): String {
            return "generated-uuid"
        }
    }
}
