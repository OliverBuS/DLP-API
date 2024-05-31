package com.dlp.dlp_api.controller

import com.dlp.dlp_api.entity.UserManagement
import com.dlp.dlp_api.exceptions.ReferenceNotFoundException
import com.dlp.dlp_api.service.UserManagementService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserManagementService,
    private val passwordEncoder: PasswordEncoder
) {
    @GetMapping
    fun getAll(
        @RequestParam(required = false, defaultValue = "10") size: Int,
        @RequestParam(required = false, defaultValue = "0") page: Int
    ): List<UserManagement> {
        return userService.getAll(page, size)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): UserManagement {
        return userService.getById(id)
    }

    @PostMapping
    fun create(@RequestBody user: UserManagement): UserManagement {
        println(user)
        print("Se ejecuto el POST de UserController")
        val encryptedPassword = passwordEncoder.encode(user.password)
        user.password = encryptedPassword

        return userService.save(user)
    }

    //Test post
    @PostMapping("/test")
    fun testPost(@RequestBody user: UserManagement): UserManagement? {
        println(user)
        print("Se ejecuto el POST de UserController")
        return null
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody user: UserManagement): UserManagement {
        if (userService.exists(id)) {
            throw ReferenceNotFoundException("User with id $id already exists")
        }
        user.id = id
        return userService.save(user)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): String {
        userService.deleteById(id)
        return "User successfully deleted"
    }

}