package com.dlp.dlp_api.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "custom_deny_list")
class CustomDenyList (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false)
    var value: String,

    @ManyToOne
    @JoinColumn(name="entity_type_id")
    @JsonIgnore
    var entityType: CustomEntityType
)