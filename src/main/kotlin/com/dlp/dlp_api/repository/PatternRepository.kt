package com.dlp.dlp_api.repository

import com.dlp.dlp_api.entity.CustomPattern
import org.springframework.data.jpa.repository.JpaRepository

interface PatternRepository : JpaRepository<CustomPattern, Long>