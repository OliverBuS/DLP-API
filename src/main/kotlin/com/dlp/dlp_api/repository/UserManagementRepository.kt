package com.dlp.dlp_api.repository

import com.dlp.dlp_api.entity.UserManagement
import org.springframework.data.jpa.repository.JpaRepository

interface UserManagementRepository : JpaRepository<UserManagement, Long>