package com.dlp.dlp_api.service

import com.dlp.dlp_api.entity.CustomEntityType
import com.dlp.dlp_api.repository.CustomEntityTypeRepository
import org.springframework.stereotype.Service

@Service
class CustomEntityTypeService(private val customEntityTypeRepository: CustomEntityTypeRepository) {
    fun getAll(): List<CustomEntityType> {
        return customEntityTypeRepository.findAll()
    }

    fun getById(id: Long): CustomEntityType? {
        return customEntityTypeRepository.findById(id).orElse(null)
    }

    fun create(customEntityType: CustomEntityType) : CustomEntityType {
        return customEntityTypeRepository.save(customEntityType)
    }
}
