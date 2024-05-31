package com.dlp.dlp_api.repository

import com.dlp.dlp_api.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>{
    fun findByEmail(email: String): User?
}