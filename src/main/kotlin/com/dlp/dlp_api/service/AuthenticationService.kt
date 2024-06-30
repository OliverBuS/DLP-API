package com.dlp.dlp_api.service

import com.dlp.dlp_api.repository.UserRepository
import com.dlp.dlp_api.utils.JwtUtil
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil,
    private val authenticationManager: AuthenticationManager
) {

    fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.email, request.password)
        )
        val user = userRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("Invalid email or password")
        val token = jwtUtil.generateToken(user)
        return AuthenticationResponse(token,
            user.firstName + " " + user.lastName,
            user.email,
            user.role.role, user.role.permissions.map { it.permission })
    }
}

data class AuthenticationRequest(val email: String, val password: String)
data class AuthenticationResponse(
    val token: String,
    val name: String,
    val email: String,
    val role: String,
    val permissions: List<String>
)
