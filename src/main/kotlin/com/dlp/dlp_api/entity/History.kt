package com.dlp.dlp_api.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "history")
class History(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "code")
    val code: String? = null,

    @Column(name = "originated_at", nullable = false)
    val originatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "origin", nullable = false)
    val origin: String,

    @Column(name = "destination", nullable = false)
    val destiny: String,

    @Column(name = "sensitive_data", nullable = false)
    val sensitiveData: String,

    @Column(name = "results", nullable = false, columnDefinition = "jsonb")
    val results: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    val level: AlertLevel,

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false)
    val action: DlpAction,

    @Column(name = "text", nullable = false)
    val text: String,

    @Column(name = "text_redacted", nullable = false)
    val textRedacted: String,

    @Column(name = "file", nullable = false)
    val file: Boolean = false,

    @Column(name = "metadata", columnDefinition = "jsonb")
    val metadata: String? = null,

) {

    enum class AlertLevel {
        High,
        Medium,
        Low
    }

    enum class DlpAction {
        Alert,
        Redact,
        Block
    }

}
