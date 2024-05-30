package com.dlp.dlp_api.entity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "network_endpoint")
data class NetworkEndpoint(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "ip", nullable = false)
    val ip: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    val status: UserStatus = UserStatus.NORMAL,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @ManyToOne
    @JoinColumn(name = "network_id", nullable = false)
    val network: Network

) {
    enum class UserStatus {
        BLOCKED,
        SUSPICIOUS,
        NORMAL
    }
}