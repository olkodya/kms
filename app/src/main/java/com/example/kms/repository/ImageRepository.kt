package com.example.kms.repository

interface ImageRepository {
    suspend fun getById(id: Int): String
}