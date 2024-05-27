package com.example.kms.repository.reposImpl

import com.example.kms.model.DTO.Key
import com.example.kms.model.DTO.KeyForm
import com.example.kms.network.api.KeyApi
import com.example.kms.repository.repos.KeyRepository

class KeyRepositoryImpl(
    private val api: KeyApi
) : KeyRepository {
    override suspend fun getByQR(QR: String): Key {
        return api.getKeyByQR(QR)
    }

    override suspend fun updateKey(id: Int, keyForm: KeyForm): Key = api.updateKey(id, keyForm)
}