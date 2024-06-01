package com.dlp.dlp_api.controller

import com.dlp.dlp_api.entity.User
import com.dlp.dlp_api.utils.JwtUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val jwtUtil: JwtUtil,
    private val authenticationManager: AuthenticationManager
) {
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<String> {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password)
        )
        SecurityContextHolder.getContext().authentication = authentication
        val user = authentication.principal as User
        val token = jwtUtil.generateToken(user)
        return ResponseEntity.ok(token)
    }

    data class LoginRequest(
        val email: String,
        val password: String
    )
}

