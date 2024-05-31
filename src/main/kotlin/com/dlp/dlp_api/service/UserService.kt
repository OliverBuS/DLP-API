package com.dlp.dlp_api.service

import com.dlp.dlp_api.entity.User
import com.dlp.dlp_api.repository.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) {
    fun getAll(page: Int, size: Int): List<User> {
        val pageRequest = PageRequest.of(page, size)
        return userRepository.findAll(pageRequest).content
    }

    fun getById(id: Long): User {
        return userRepository.findById(id).orElse(null)
    }

    fun create(user: User): User {
        return userRepository.save(user)
    }

    fun exists(id: Long): Boolean {
        return userRepository.existsById(id)
    }

    fun deleteById(id: Long) {
        userRepository.deleteById(id)
    }

}