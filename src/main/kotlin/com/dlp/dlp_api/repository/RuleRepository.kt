package com.dlp.dlp_api.repository

import com.dlp.dlp_api.entity.Rule
import org.springframework.data.jpa.repository.JpaRepository

interface RuleRepository : JpaRepository<Rule, Long>