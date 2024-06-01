package com.dlp.dlp_api.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "networks")
class Network(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "subnet", nullable = false)
    var subnet: String,

    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "description", nullable = false)
    var description: String,

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "created_by", updatable = false)
    var createdBy: Long? = null,

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_by")
    var updatedBy: Long? = null,

    @OneToMany(mappedBy = "network")
    var endpoints: List<NetworkEndpoint> = emptyList(),

    @ManyToMany
    @JoinTable(
        name = "groups_rules",
        joinColumns = [JoinColumn(name = "network_id")],
        inverseJoinColumns = [JoinColumn(name = "rule_id")]
    )
    @JsonIgnoreProperties("networks")
    var rules: List<Rule>
)
