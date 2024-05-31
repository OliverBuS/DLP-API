package com.dlp.dlp_api.controller

import com.dlp.dlp_api.entity.Rule
import com.dlp.dlp_api.exceptions.ReferenceNotFoundException
import com.dlp.dlp_api.service.RuleService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rules")
class RuleController(private val ruleService: RuleService) {
    @GetMapping
    fun getAll(
        @RequestParam(name = "page", defaultValue = "0") pageNumber: Int,
        @RequestParam(name = "size", defaultValue = "10") pageSize: Int
    ): List<Rule> {
        return ruleService.getAllPaged(pageNumber, pageSize)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): Rule {
        return ruleService.getById(id)
    }

    @PostMapping
    fun create(@RequestBody rule: Rule): Rule {
        return ruleService.save(rule)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody rule: Rule): Rule {
        if(!ruleService.exists(id)) {
            throw ReferenceNotFoundException("Rule with id $id not found")
        }

        rule.id = id
        return ruleService.save(rule)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): String {
        ruleService.delete(id)
        return "Rule successfully deleted"
    }

}