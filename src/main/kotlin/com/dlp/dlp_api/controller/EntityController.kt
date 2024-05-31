package com.dlp.dlp_api.controller

import com.dlp.dlp_api.entity.CustomEntityType
import com.dlp.dlp_api.service.EntityService
import jakarta.persistence.EntityNotFoundException
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/entities")
class EntityController( private val customEntityTypeService: EntityService) {

    @GetMapping
    fun getAll(
        @RequestParam(name = "page", defaultValue = "0") pageNumber: Int,
        @RequestParam(name = "size", defaultValue = "10") pageSize: Int)
    : List<CustomEntityType> {
        return customEntityTypeService.getAll(pageNumber, pageSize)
    }

    @GetMapping("{id}")
    fun getById(@PathVariable id: Long): CustomEntityType? {
        return customEntityTypeService.getById(id)
    }

    @PostMapping
    fun create(@RequestBody customEntityType: CustomEntityType): CustomEntityType {
        return customEntityTypeService.save(customEntityType)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody customEntityType: CustomEntityType): CustomEntityType {
        if(!customEntityTypeService.exists(id)) {
            throw EntityNotFoundException("Entity with id=$id does not exist")
        }
        customEntityType.id = id
        return customEntityTypeService.save(customEntityType)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): String {
        customEntityTypeService.delete(id)
        return "Entity successfully deleted"
    }
}