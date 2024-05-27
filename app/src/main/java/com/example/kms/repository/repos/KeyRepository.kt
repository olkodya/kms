package com.example.kms.repository.repos

import com.example.kms.model.DTO.Key
import com.example.kms.model.DTO.KeyForm

interface KeyRepository {
    suspend fun getByQR(QR: String): Key

    suspend fun updateKey(id: Int, keyForm: KeyForm): Key

}