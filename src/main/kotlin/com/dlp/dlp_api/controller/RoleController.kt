package com.dlp.dlp_api.controller

import com.dlp.dlp_api.dto.CreateRoleDTO
import com.dlp.dlp_api.entity.Permission
import com.dlp.dlp_api.entity.Role
import com.dlp.dlp_api.exceptions.ReferenceNotFoundException
import com.dlp.dlp_api.repository.RoleRepository
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/roles")
class RoleController(private val roleRepository: RoleRepository) {

    @GetMapping
    fun getAll(
        @RequestParam(required = false, defaultValue = "10") size: Int,
        @RequestParam(required = false, defaultValue = "0") page: Int
    ): List<Role> {
        val pageRequest = PageRequest.of(page, size)
        return roleRepository.findAll(pageRequest).content
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): Role {
        return roleRepository.findById(id).orElse(null)
    }

    @PostMapping
    fun create(@RequestBody role: CreateRoleDTO): Role {

        // References of the default permissions in the database
        val permissions = role.permissions.mapNotNull {
            permissionsDefault[it]?.let { permission ->
                Permission(permission, it)
            }
        }.toSet()

        val newRole = Role(
            role = role.role,
            permissions = permissions,
            createdBy = null,
            updatedBy = null
        )
        return roleRepository.save(newRole)

    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody role: CreateRoleDTO): Role {
        if (roleRepository.existsById(id)) {
            throw ReferenceNotFoundException("Role with id $id already exists")
        }

        val permissions = role.permissions.mapNotNull {
            permissionsDefault[it]?.let { permission ->
                Permission(permission, it)
            }
        }.toSet()

        val roleToUpdate = roleRepository.findById(id)

        val updatedRole = Role(
            id = id,
            role = role.role,
            permissions = permissions,
            updatedBy = null // TODO: get current user with JWT token
        )

        return roleRepository.save(updatedRole)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): String {
        roleRepository.deleteById(id)
        return "Role successfully deleted"
    }

    val permissionsDefault = mapOf(
        "Context-Viewer" to 1,
        "Dashboard-Viewer" to 2,
        "Event-Viewer" to 3,
    )
}



