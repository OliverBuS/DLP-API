package com.dlp.dlp_api.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
data class UserManagement(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq1")
    @SequenceGenerator(name = "users_id_seq1", sequenceName = "users_id_seq1", allocationSize = 1)
    var id: Long? = null,

    @Column(nullable = false)
    val firstName: String,

    @Column(nullable = false)
    val lastName: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    var password: String,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    val role: Role,

    @Column(name = "created_by", updatable = false)
    val createdBy: Long? = null,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_by")
    val updatedBy: Long? = null,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),

    )