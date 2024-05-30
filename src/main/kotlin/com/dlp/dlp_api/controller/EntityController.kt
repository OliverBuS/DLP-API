package com.dlp.dlp_api.controller

import com.dlp.dlp_api.entity.CustomEntityType
import com.dlp.dlp_api.service.CustomEntityTypeService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/entities")
class EntityController( private val customEntityTypeService: CustomEntityTypeService) {

    @GetMapping
    fun getAll(): List<CustomEntityType> {
        return customEntityTypeService.getAll()
    }

    @GetMapping("{id}")
    fun getById(@PathVariable id: Long): CustomEntityType? {
        return customEntityTypeService.getById(id)
    }

    @PostMapping
    fun create(@RequestBody customEntityType: CustomEntityType): CustomEntityType {
        return customEntityTypeService.create(customEntityType)
    }
}