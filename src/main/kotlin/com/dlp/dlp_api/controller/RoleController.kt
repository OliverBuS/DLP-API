package com.dlp.dlp_api.controller

import com.dlp.dlp_api.entity.Role
import com.dlp.dlp_api.exceptions.ReferenceNotFoundException
import com.dlp.dlp_api.service.RoleService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/roles")
class RoleController(private val roleService: RoleService) {

    @GetMapping
    fun getAll(
        @RequestParam(required = false, defaultValue = "10") size: Int,
        @RequestParam(required = false, defaultValue = "0") page: Int
    ): List<Role> {
        return roleService.findAll(page, size)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): Role {
        return roleService.findById(id)
    }

    @PostMapping
    fun create(@RequestBody role: Role): Role {
        return roleService.create(role)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody role: Role): Role {
        if (roleService.exists(id)) {
            throw ReferenceNotFoundException("Role with id $id already exists")
        }
        role.id = id
        return roleService.create(role)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): String {
        roleService.deleteById(id)
        return "Role successfully deleted"
    }

}