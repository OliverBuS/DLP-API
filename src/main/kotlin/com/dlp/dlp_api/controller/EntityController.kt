package com.dlp.dlp_api.controller

import com.dlp.dlp_api.dto.CreateEntityDTO
import com.dlp.dlp_api.entity.CustomContextWord
import com.dlp.dlp_api.entity.CustomDenyList
import com.dlp.dlp_api.entity.CustomEntityType
import com.dlp.dlp_api.entity.CustomPattern
import com.dlp.dlp_api.exceptions.ReferenceNotFoundException
import com.dlp.dlp_api.repository.ContextWordRepository
import com.dlp.dlp_api.repository.DenyListRepository
import com.dlp.dlp_api.repository.EntityRepository
import com.dlp.dlp_api.repository.PatternRepository
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/entities")
class EntityController(
    private val entityRepository: EntityRepository,
    private val contextWordRepository: ContextWordRepository,
    private val denyListRepository: DenyListRepository,
    private val patternRepository: PatternRepository
) {

    @GetMapping
    fun getAll(
        @RequestParam(name = "page", defaultValue = "0") pageNumber: Int,
        @RequestParam(name = "size", defaultValue = "10") pageSize: Int
    )
            : List<CustomEntityType> {
        val pageRequest = PageRequest.of(pageNumber, pageSize)
        return entityRepository.findAll(pageRequest).content
    }

    @GetMapping("{id}")
    fun getById(@PathVariable id: Long): CustomEntityType? {
        return entityRepository.findById(id).orElse(null)
    }

    @PostMapping
    fun create(@RequestBody createEntity: CreateEntityDTO): CustomEntityType {

        val entity = CustomEntityType(
            name = createEntity.name,
            description = createEntity.description,
            detectionType = createEntity.detectionType,
            createdBy = null, // TODO: get user id from JWT Token
            updatedBy = null,
        )

        val createdEntity = entityRepository.save(entity)
        contextWordRepository.saveAll(createEntity.contextWords.map {
            CustomContextWord(
                word = it,
                entityType = createdEntity
            )
        })
        denyListRepository.saveAll(createEntity.denyList.map { CustomDenyList(value = it, entityType = createdEntity) })
        patternRepository.saveAll(createEntity.patterns.map {
            CustomPattern(
                name = it.name,
                regex = it.regex,
                score = it.score,
                entityType = createdEntity
            )
        })

        return entityRepository.findById(createdEntity.id!!).get()
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody updateEntity: CreateEntityDTO): CustomEntityType {
        val entityOptional = entityRepository.findById(id)
        if (entityOptional.isEmpty) {
            throw ReferenceNotFoundException("Entity with id $id not found")
        }
        val entity = entityOptional.get()
        entityRepository.deleteById(id)

        val entityToUpdate = CustomEntityType(
            id = id,
            name = updateEntity.name,
            description = updateEntity.description,
            detectionType = updateEntity.detectionType,
            createdBy = entity.createdBy,
            updatedBy = null, // TODO: get user id from JWT Token
        )

        val updatedEntity = entityRepository.save(entity)
        contextWordRepository.saveAll(updateEntity.contextWords.map {
            CustomContextWord(
                word = it,
                entityType = updatedEntity
            )
        })
        denyListRepository.saveAll(updateEntity.denyList.map { CustomDenyList(value = it, entityType = updatedEntity) })
        patternRepository.saveAll(updateEntity.patterns.map {
            CustomPattern(
                name = it.name,
                regex = it.regex,
                score = it.score,
                entityType = updatedEntity
            )
        })

        return entityRepository.findById(id).get()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): String {
        entityRepository.deleteById(id)
        return "Entity successfully deleted"
    }
}