package com.example.kms.repository

import com.example.kms.model.Watch

interface WatchRepository {
    suspend fun getWatches(): List<Watch>

}