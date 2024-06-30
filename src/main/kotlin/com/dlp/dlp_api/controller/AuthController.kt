package com.dlp.dlp_api.controller

import com.dlp.dlp_api.service.AuthenticationRequest
import com.dlp.dlp_api.service.AuthenticationResponse
import com.dlp.dlp_api.service.AuthenticationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(private val authenticationService: AuthenticationService) {

    @PostMapping("/login")
    fun authenticate(@RequestBody request: AuthenticationRequest): ResponseEntity<AuthenticationResponse> {
        return ResponseEntity.ok(authenticationService.authenticate(request))
    }
}

