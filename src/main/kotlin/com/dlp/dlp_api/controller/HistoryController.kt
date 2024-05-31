package com.dlp.dlp_api.controller

import com.dlp.dlp_api.entity.History
import com.dlp.dlp_api.model.SummaryData
import com.dlp.dlp_api.service.HistoryService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/history")
class HistoryController (private val historyService: HistoryService) {

    @GetMapping
    fun getAll(
        @RequestParam(name = "page", defaultValue = "0") pageNumber: Int,
        @RequestParam(name = "size", defaultValue = "10") pageSize: Int
    ): List<History> {
        val result = historyService.getAll(pageNumber, pageSize)
        return result
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): History {
        return historyService.getById(id)
    }

    @GetMapping("/summary")
    fun getSummary(): List<SummaryData> {
        return historyService.getSummary()
    }


}