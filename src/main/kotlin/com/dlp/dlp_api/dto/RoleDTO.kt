package com.dlp.dlp_api.dto

class CreateRoleDTO(
    var role: String,
    var permissions: Set<String>
)