package com.dlp.dlp_api.repository

import com.dlp.dlp_api.entity.History
import com.dlp.dlp_api.model.SummaryData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface HistoryRepository : JpaRepository<History, Long> {
    @Query(value =
        "select\n" +
                "    level,\n" +
                "    count(*) as alertas,\n" +
                "    DATE(originated_at)\n" +
                "from history h\n" +
                "group by DATE(originated_at), level;",
        nativeQuery = true
    )
    fun getSummary(): List<SummaryData>
}