package com.dlp.dlp_api.model

import com.dlp.dlp_api.entity.History

data class AlertCountByLevel(
        val level: History.AlertLevel,
        val count: Long
)
