package com.example.kms.repository

import com.example.kms.model.Key
import com.example.kms.model.KeyForm

interface KeyRepository {
    suspend fun getByQR(QR: String): Key

    suspend fun updateKey(id: Int, keyForm: KeyForm): Key

}