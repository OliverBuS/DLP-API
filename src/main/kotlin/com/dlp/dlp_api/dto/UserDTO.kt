package com.dlp.dlp_api.dto

class CreateUserDTO(
    var firstName: String,
    var lastName: String,
    var email: String,
    var password: String,
    var roleId: Long,
)