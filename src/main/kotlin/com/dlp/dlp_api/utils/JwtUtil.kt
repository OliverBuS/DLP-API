package com.dlp.dlp_api.utils

import com.dlp.dlp_api.config.JwtConfig
import com.dlp.dlp_api.entity.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil(private val jwtConfig: JwtConfig) {
    fun generateToken(user: User): String {
        val claims = mapOf(
            "name" to user.firstName + " " + user.lastName,
            "email" to user.email,
            "role" to user.role.role,
            "permissions" to user.role.permissions.map { it.permission }
        )

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(user.email)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + jwtConfig.expiration * 1000))
            .setIssuer(jwtConfig.issuer)
            .signWith(Keys.hmacShaKeyFor(jwtConfig.secret.toByteArray()), SignatureAlgorithm.HS256)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(jwtConfig.secret.toByteArray()))
                .build()
                .parseClaimsJws(token)
            true
        } catch (ex: Exception) {
            false
        }
    }

    fun getEmailFromToken(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(jwtConfig.secret.toByteArray()))
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }
}