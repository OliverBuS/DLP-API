package com.dlp.dlp_api.repository

import com.dlp.dlp_api.entity.CustomDenyList
import org.springframework.data.jpa.repository.JpaRepository

interface DenyListRepository : JpaRepository<CustomDenyList, Long>