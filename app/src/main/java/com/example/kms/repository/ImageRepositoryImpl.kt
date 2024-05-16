package com.example.kms.repository

import com.example.kms.network.api.ImageApi

class ImageRepositoryImpl(
    private val api: ImageApi

) : ImageRepository {
    override suspend fun getById(id: Int): String {
        val responseBody = api.getImageById(id)
        val bytes = responseBody.bytes()
        return bytes.toString(Charsets.UTF_8)
    }
}