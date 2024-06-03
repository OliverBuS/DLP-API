package com.dlp.dlp_api.service
import com.dlp.dlp_api.model.AlertCountByLevel
import com.dlp.dlp_api.repository.HistoryRepository
import org.springframework.stereotype.Service

@Service
class HistoryService(private val historyRepository: HistoryRepository) {

    fun getCountAlertsByLevel(): List<AlertCountByLevel> {
        return historyRepository.countAlertsByLevel()
    }
}
