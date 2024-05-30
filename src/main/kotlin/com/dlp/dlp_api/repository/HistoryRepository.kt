package com.dlp.dlp_api.repository

import com.dlp.dlp_api.entity.History
import org.springframework.data.jpa.repository.JpaRepository

interface HistoryRepository : JpaRepository<History, Long>