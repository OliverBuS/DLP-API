package com.dlp.dlp_api.service

import com.dlp.dlp_api.entity.UserManagement
import com.dlp.dlp_api.repository.UserManagementRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class UserManagementService(private val userManagementRepository: UserManagementRepository) {
    fun getAll(page: Int, size: Int): List<UserManagement> {
        val pageRequest = PageRequest.of(page, size)
        return userManagementRepository.findAll(pageRequest).content
    }

    fun getById(id: Long): UserManagement {
        return userManagementRepository.findById(id).orElse(null)
    }

    fun save(userManagement: UserManagement): UserManagement {
        return userManagementRepository.save(userManagement)
    }

    fun exists(id: Long): Boolean {
        return userManagementRepository.existsById(id)
    }

    fun deleteById(id: Long) {
        userManagementRepository.deleteById(id)
    }
}