package com.dlp.dlp_api.controller

import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.aspectj.weaver.ast.Literal
import org.aspectj.weaver.ast.Test
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/test")
class TestController {

    @GetMapping
    @ApiResponse(responseCode = "200", description = "Hello World")
    fun test(): String {
        return "Hello World"
    }

    //Test for Auth, User must be Admin
    @GetMapping("/auth")
    fun auth(): String {
        return "Hello World Auth"
    }
}
