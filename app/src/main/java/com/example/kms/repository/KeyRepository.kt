package com.example.kms.repository

import com.example.kms.model.Key

interface KeyRepository {
    suspend fun getByQR(QR: String): Key

}