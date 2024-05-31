package com.dlp.dlp_api.service

import com.dlp.dlp_api.entity.Network
import com.dlp.dlp_api.repository.NetworkRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class NetworkService(private val networkRepository: NetworkRepository) {
    fun findAll(page: Int, size: Int): List<Network> {
        val pageRequest = PageRequest.of(page, size)
        return networkRepository.findAll(pageRequest).content
    }

    fun findById(id: Long): Network {
        return networkRepository.findById(id).orElseThrow()
    }

    fun save(network: Network): Network {
        return networkRepository.save(network)
    }

    fun delete(id: Long) {
        networkRepository.deleteById(id)
    }

    fun exists(id: Long): Boolean {
        return networkRepository.existsById(id)
    }

}
