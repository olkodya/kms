package com.example.kms.repository.reposImpl

import com.example.kms.model.DTO.Watch
import com.example.kms.network.api.WatchApi
import com.example.kms.repository.repos.WatchRepository

class WatchRepositoryImpl(
    private val api: WatchApi
) : WatchRepository {
    override suspend fun getWatches(): List<Watch> {
        return api.getWatches()
    }
}