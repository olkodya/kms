package com.example.kms.repository

import com.example.kms.model.LoginDto
import com.example.kms.model.TokenDto
import com.example.kms.model.Watch
import com.example.kms.network.api.UserApi
import com.example.kms.network.api.WatchApi

class WatchRepositoryImpl(
    private val api: WatchApi
) : WatchRepository {
    override suspend fun getWatches(): List<Watch> {
        return api.getWatches()
    }
}