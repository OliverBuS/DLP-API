package com.dlp.dlp_api.entity

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "roles")
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rols_id_seq")
    @SequenceGenerator(name = "rols_id_seq", sequenceName = "rols_id_seq", allocationSize = 1)
    var id: Long? = null,

    @Column(nullable = false, unique = true)
    val role: String,

    @ManyToMany
    @JoinTable(
        name = "roles_permissions",
        joinColumns = [JoinColumn(name = "role_id")],
        inverseJoinColumns = [JoinColumn(name = "permission_id")]
    )
    val permissions: Set<Permission> = emptySet(),

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "created_by")
    val createdBy: Int? = null,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_by")
    val updatedBy: Int? = null
)