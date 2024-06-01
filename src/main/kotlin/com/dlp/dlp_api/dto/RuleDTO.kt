package com.dlp.dlp_api.dto

import com.dlp.dlp_api.entity.Rule

class CreateRuleDTO(
    var code: String,
    var name: String,
    var entityTypeId: Long,
    var action: Rule.Action,
    var level: Rule.AlertLevel,
    var description: String,
    var status: Boolean,
    var confidenceLevel: Double,
    var hitsLower: Int,
    var hitsUpper: Int,
    var alerts: Boolean,
    var networkIds: List<Long> = emptyList()
)