package com.dlp.dlp_api.controller

import com.dlp.dlp_api.dto.CreateNetworkDTO
import com.dlp.dlp_api.entity.Network
import com.dlp.dlp_api.exceptions.ReferenceNotFoundException
import com.dlp.dlp_api.repository.NetworkRepository
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/network")
class NetworkController(private val networkRepository: NetworkRepository) {

    @GetMapping("/")
    fun getAll(
        @RequestParam(required = false, defaultValue = "10") size: Int,
        @RequestParam(required = false, defaultValue = "0") page: Int
    ): List<Network> {
        val pageRequest = PageRequest.of(page, size)
        return networkRepository.findAll(pageRequest).content
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): Network {
        return networkRepository.findById(id).orElse(null)
    }

    @PostMapping
    fun create(@Valid @RequestBody network: CreateNetworkDTO): Network {
        val newNetwork = Network(
            subnet = network.subnet,
            name = network.name,
            description = network.description,
            createdBy = null,
            updatedBy = null,
            rules = emptyList()
        )
        return networkRepository.save(newNetwork)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @Valid @RequestBody network: CreateNetworkDTO): Network {

        val networkOptional = networkRepository.findById(id)
        if (networkOptional.isEmpty) {
            throw ReferenceNotFoundException("Network with id $id not found")
        }
        val networkToUpdate = networkOptional.get()
        networkRepository.deleteById(id)

        val updatedNetwork = Network(
            id = id,
            subnet = network.subnet,
            name = network.name,
            description = network.description,
            createdBy = networkToUpdate.createdBy,
            updatedBy = null, // TODO: get current user with JWT token
            rules = networkToUpdate.rules,
            endpoints = networkToUpdate.endpoints
        )

        return networkRepository.save(updatedNetwork)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): String {
        networkRepository.deleteById(id)
        return "Network successfully deleted"
    }

}