package com.dlp.dlp_api.repository

import com.dlp.dlp_api.entity.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository: JpaRepository<Role, Long>