package com.dlp.dlp_api.repository

import com.dlp.dlp_api.entity.Network
import org.springframework.data.jpa.repository.JpaRepository

interface NetworkRepository : JpaRepository<Network, Long>