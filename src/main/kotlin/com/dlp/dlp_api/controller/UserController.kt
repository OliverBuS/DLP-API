package com.dlp.dlp_api.controller

import com.dlp.dlp_api.dto.CreateUserDTO
import com.dlp.dlp_api.entity.UserManagement
import com.dlp.dlp_api.exceptions.ReferenceNotFoundException
import com.dlp.dlp_api.repository.RoleRepository
import com.dlp.dlp_api.repository.UserManagementRepository
import org.springframework.data.domain.PageRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userRepository: UserManagementRepository,
    private val rolesRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @GetMapping
    fun getAll(
        @RequestParam(required = false, defaultValue = "10") size: Int,
        @RequestParam(required = false, defaultValue = "0") page: Int
    ): List<UserManagement> {
        val pageRequest = PageRequest.of(page, size)
        return userRepository.findAll(pageRequest).content
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): UserManagement {
        return userRepository.findById(id).orElseThrow { ReferenceNotFoundException("User with id $id not found") }
    }

    @PostMapping
    fun create(@RequestBody user: CreateUserDTO): UserManagement {
        val role = rolesRepository.findById(user.roleId)
            .orElseThrow { ReferenceNotFoundException("Role with id $user.roleId not found") }
        val userToCreate = UserManagement(
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            password = passwordEncoder.encode(user.password),
            role = role,
            createdBy = null, // TODO: get current user with JWT token
        )

        return userRepository.save(userToCreate)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody user: CreateUserDTO): UserManagement {
        val userOptional = userRepository.findById(id)
        if (userOptional.isEmpty) {
            throw ReferenceNotFoundException("User with id $id not found")
        }
        val userToUpdate = userOptional.get()
        userRepository.delete(userToUpdate)
        val userUpdated = UserManagement(
            id = id,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            password = user.password,
            role = userToUpdate.role,
            createdBy = userToUpdate.createdBy,
            createdAt = userToUpdate.createdAt,
        )

        return userRepository.save(userUpdated)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): String {
        userRepository.deleteById(id)
        return "User successfully deleted"
    }

}