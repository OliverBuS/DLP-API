package com.dlp.dlp_api.service

import com.dlp.dlp_api.entity.History
import com.dlp.dlp_api.repository.HistoryRepository
import com.dlp.dlp_api.model.SummaryData
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class HistoryService (private val historyRepository: HistoryRepository) {
    fun getAll(pageNumber: Int, pageSize: Int): List<History> {
        val pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id"))
        return historyRepository.findAll(pageable).content
    }

    fun getById(id: Long): History {
        return historyRepository.findById(id).orElseThrow()
    }

    fun getSummary(): List<SummaryData> {
        return historyRepository.getSummary()
    }
}