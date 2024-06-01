package com.dlp.dlp_api.repository

import com.dlp.dlp_api.entity.CustomContextWord
import org.springframework.data.jpa.repository.JpaRepository

interface ContextWordRepository : JpaRepository<CustomContextWord, Long>