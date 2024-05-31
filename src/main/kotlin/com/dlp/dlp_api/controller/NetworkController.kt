package com.dlp.dlp_api.controller

import com.dlp.dlp_api.entity.Network
import com.dlp.dlp_api.exceptions.ReferenceNotFoundException
import com.dlp.dlp_api.service.NetworkService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/network")
class NetworkController (private val networkService: NetworkService) {

    @GetMapping("/")
    fun getAll(
        @RequestParam(required = false, defaultValue = "0") size: Int,
        @RequestParam(required = false, defaultValue = "0") page: Int
    ): List<Network> {
        return networkService.findAll(page, size)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): Network {
        return networkService.findById(id)
    }

    @PostMapping
    fun create(@RequestBody network: Network): Network {
        return networkService.save(network)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody network: Network): Network {
        if(!networkService.exists(id)) {
            throw ReferenceNotFoundException("Network with id $id not found")
        }
        network.id = id
        return networkService.save(network)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): String {
        networkService.delete(id)
        return "Network successfully deleted"
    }
}