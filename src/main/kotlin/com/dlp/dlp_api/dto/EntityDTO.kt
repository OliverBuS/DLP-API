package com.dlp.dlp_api.dto

import com.dlp.dlp_api.entity.CustomEntityType

class CreateEntityDTO(
    var name: String,
    var description: String,
    var detectionType: CustomEntityType.DetectionType,
    var denyList: List<String> = emptyList(),
    var patterns: List<CreatePatternDTO> = emptyList(),
    var contextWords: List<String> = emptyList(),
)

class CreatePatternDTO(
    var name: String,
    var regex: String,
    var score: Float
)
