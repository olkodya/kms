package com.example.kms.repository

import com.example.kms.model.Key
import com.example.kms.network.api.KeyApi

class KeyRepositoryImpl(
    private val api: KeyApi
) : KeyRepository {
    override suspend fun getByQR(QR: String): Key {
        return api.getKeyByQR(QR)
    }
}