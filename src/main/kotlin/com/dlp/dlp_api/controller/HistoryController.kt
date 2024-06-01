package com.dlp.dlp_api.controller

import com.dlp.dlp_api.entity.History
import com.dlp.dlp_api.model.SummaryData
import com.dlp.dlp_api.repository.HistoryRepository
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/history")
class HistoryController(private val historyRepository: HistoryRepository) {

    @GetMapping
    fun getAll(
        @RequestParam(name = "page", defaultValue = "0") pageNumber: Int,
        @RequestParam(name = "size", defaultValue = "10") pageSize: Int
    ): List<History> {
        val pageRequest = PageRequest.of(pageNumber, pageSize)
        val result = historyRepository.findAll(pageRequest).content
        return result
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): History {
        return historyRepository.findById(id).get()
    }

    @GetMapping("/summary")
    fun getSummary(): List<SummaryData> {
        return historyRepository.getSummary()
    }


}