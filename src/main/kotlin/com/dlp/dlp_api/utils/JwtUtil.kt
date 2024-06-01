package com.dlp.dlp_api.utils

import com.dlp.dlp_api.entity.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtil {
    private val secret = "pucpguardian123OliverAngelG123PUCPGuardian721"
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
    private val expirationInMs = 86400000 // 24 hours

    fun generateToken(user: User): String {
        val claims = HashMap<String, Any>()
        claims["sub"] = user.email
        claims["role"] = user.role
        claims["permissions"] = user.role.permissions.map { it.permission }

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expirationInMs))
            .signWith(secretKey)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token) // Build parser first
            true
        } catch (ex: Exception) {
            false
        }
    }

    fun getEmailFromToken(token: String): String {
        val claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body
        return claims.subject
    }
}
