package com.example.kms.repository.repos

import com.example.kms.model.DTO.Watch

interface WatchRepository {
    suspend fun getWatches(): List<Watch>

}