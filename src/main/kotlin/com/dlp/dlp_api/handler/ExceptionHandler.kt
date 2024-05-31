package com.dlp.dlp_api.handler

import com.dlp.dlp_api.exceptions.ReferenceNotFoundException
import com.dlp.dlp_api.model.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(ReferenceNotFoundException::class)
    fun handleReferenceNotFoundException(e: ReferenceNotFoundException): ResponseEntity<ErrorResponse> {
        val errorMessage = ErrorResponse("Reference not found")
        return ResponseEntity.status(404).body(errorMessage)
    }
}