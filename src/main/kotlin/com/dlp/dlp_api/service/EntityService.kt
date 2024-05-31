package com.dlp.dlp_api.service

import com.dlp.dlp_api.entity.CustomEntityType
import com.dlp.dlp_api.repository.CustomEntityTypeRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class EntityService(private val customEntityTypeRepository: CustomEntityTypeRepository) {
    fun getAll(page: Int, size: Int): List<CustomEntityType> {
        val pageable = PageRequest.of(page, size)
        return customEntityTypeRepository.findAll(pageable).content
    }

    fun getById(id: Long): CustomEntityType? {
        return customEntityTypeRepository.findById(id).orElse(null)
    }

    fun save(customEntityType: CustomEntityType) : CustomEntityType {
        return customEntityTypeRepository.save(customEntityType)
    }

    fun delete(id: Long) {
        customEntityTypeRepository.deleteById(id)
    }

    fun exists(id: Long): Boolean {
        return customEntityTypeRepository.existsById(id)
    }
}
