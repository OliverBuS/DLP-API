package com.dlp.dlp_api.repository

import com.dlp.dlp_api.entity.CustomEntityType
import org.springframework.data.jpa.repository.JpaRepository

interface CustomEntityTypeRepository : JpaRepository<CustomEntityType, Long>