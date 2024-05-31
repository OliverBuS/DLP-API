package com.dlp.dlp_api.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "networks")
class Network(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "subnet", nullable = false)
    val subnet: String,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "description", nullable = false)
    val description: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "created_by", nullable = false)
    val createdBy: Long,

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_by", nullable = false)
    val updatedBy: Long,

    @OneToMany(mappedBy = "network", cascade = [CascadeType.ALL], orphanRemoval = true)
    val endpoints: List<NetworkEndpoint> = emptyList(),

    @ManyToMany
    @JoinTable(
        name = "groups_rules",
        joinColumns = [JoinColumn(name = "network_id")],
        inverseJoinColumns = [JoinColumn(name = "rule_id")]
    )
    val rules: List<Rule>)
