package com.dlp.dlp_api.controller

import com.dlp.dlp_api.dto.CreateRuleDTO
import com.dlp.dlp_api.entity.Rule
import com.dlp.dlp_api.exceptions.ReferenceNotFoundException
import com.dlp.dlp_api.repository.EntityRepository
import com.dlp.dlp_api.repository.NetworkRepository
import com.dlp.dlp_api.repository.RuleRepository
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/rules")
class RuleController(
    private val entityRepository: EntityRepository,
    private val networkRepository: NetworkRepository,
    private val ruleRepository: RuleRepository
) {
    @GetMapping
    fun getAll(
        @RequestParam(name = "page", defaultValue = "0") pageNumber: Int,
        @RequestParam(name = "size", defaultValue = "10") pageSize: Int
    ): List<Rule> {
        val pageable = PageRequest.of(pageNumber, pageSize)
        return ruleRepository.findAll(pageable).content
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): Rule {
        return ruleRepository.findById(id).orElseThrow { ReferenceNotFoundException("Rule with id $id not found") }
    }

    @PostMapping
    fun create(@RequestBody rule: CreateRuleDTO): Rule {
        val entityOptional = entityRepository.findById(rule.entityTypeId)
        if (entityOptional.isEmpty) {
            throw ReferenceNotFoundException("Entity with id $rule.entityTypeId not found")
        }
        val entity = entityOptional.get()

        val networks = networkRepository.findAllById(rule.networkIds).toSet()

        val newRule = Rule(
            code = rule.code,
            name = rule.name,
            entityType = entity,
            action = rule.action,
            level = rule.level,
            description = rule.description,
            status = rule.status,
            confidenceLevel = rule.confidenceLevel,
            hitsLower = rule.hitsLower,
            hitsUpper = rule.hitsUpper,
            alerts = rule.alerts,
            networks = networks
        )
        return ruleRepository.save(newRule)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody rule: CreateRuleDTO): Rule {
        val ruleOptional = ruleRepository.findById(id)
        if (ruleOptional.isEmpty) {
            throw ReferenceNotFoundException("Rule with id $id not found")
        }
        val ruleToUpdate = ruleOptional.get()

        val entityOptional = entityRepository.findById(rule.entityTypeId)
        if (entityOptional.isEmpty) {
            throw ReferenceNotFoundException("Entity with id $ruleToUpdate.entityTypeId not found")
        }
        val entity = entityOptional.get()

        ruleRepository.deleteById(id)

        val updatedRule = Rule(
            id = id,
            code = rule.code,
            name = rule.name,
            entityType = entity,
            action = rule.action,
            level = rule.level,
            description = rule.description,
            status = rule.status,
            confidenceLevel = rule.confidenceLevel,
            hitsLower = rule.hitsLower,
            hitsUpper = rule.hitsUpper,
            alerts = rule.alerts,
            updatedBy = null, // TODO: get current user with JWT token
            networks = ruleToUpdate.networks
        )

        return ruleRepository.save(updatedRule)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): String {
        ruleRepository.deleteById(id)
        return "Rule successfully deleted"
    }

}