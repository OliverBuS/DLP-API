package com.dlp.dlp_api.service

import com.dlp.dlp_api.entity.Rule
import com.dlp.dlp_api.repository.RuleRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class RuleService (private val ruleRepository: RuleRepository) {
    fun getAllPaged(pageNumber: Int, pageSize: Int): List<Rule> {
        val page = PageRequest.of(pageNumber, pageSize)
        return ruleRepository.findAll(page).content
    }

    fun getById(id: Long): Rule {
        return ruleRepository.findById(id).orElseThrow()
    }

    fun save(rule: Rule): Rule {
        return ruleRepository.save(rule)
    }

    fun delete(id: Long) {
        ruleRepository.deleteById(id)
    }

    fun exists(id: Long): Boolean {
        return ruleRepository.existsById(id)
    }

}