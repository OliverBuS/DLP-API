package com.dlp.dlp_api.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "rules")
class Rule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "codigo")
    var code: String? = null,

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

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "created_by", updatable = false)
    val createdBy: Long? = null,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_by")
    val updatedBy: Long? = null,

    @ManyToMany
    @JoinTable(
        name = "groups_rules",
        joinColumns = [JoinColumn(name = "rule_id")],
        inverseJoinColumns = [JoinColumn(name = "network_id")]
    )
    @JsonIgnoreProperties("rules")
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
