package com.dlp.dlp_api.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfig {
    @Value("\${cors.allowed.origins}")
    private lateinit var allowedOrigins: String

    @Value("\${cors.allowed.methods}")
    private lateinit var allowedMethods: String

    @Value("\${cors.allowed.headers}")
    private lateinit var allowedHeaders: String

    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = this@CorsConfig.allowedOrigins.split(",")
            allowedMethods = this@CorsConfig.allowedMethods.split(",")
            allowedHeaders = this@CorsConfig.allowedHeaders.split(",")
            allowCredentials = true
        }
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun corsFilter(): CorsFilter {
        return CorsFilter(corsConfigurationSource())
    }
}
