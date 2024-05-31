package com.dlp.dlp_api.service

import com.dlp.dlp_api.entity.Role
import com.dlp.dlp_api.repository.RoleRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class RoleService(private val roleRepository: RoleRepository) {
    fun findAll(page: Int, size: Int): List<Role> {
        val pageRequest = PageRequest.of(page, size)
        return roleRepository.findAll(pageRequest).content
    }

    fun findById(id: Long): Role {
        return roleRepository.findById(id).orElse(null)
    }

    fun create(role: Role): Role {
        return roleRepository.save(role)
    }

    fun exists(id: Long): Boolean {
        return roleRepository.existsById(id)
    }

    fun deleteById(id: Long) {
        roleRepository.deleteById(id)
    }
}