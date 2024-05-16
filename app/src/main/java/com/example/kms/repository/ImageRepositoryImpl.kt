package com.example.kms.repository

import com.example.kms.network.api.ImageApi
import java.io.ByteArrayOutputStream

class ImageRepositoryImpl(
    private val api: ImageApi

) : ImageRepository {
    override suspend fun getById(id: Int): ByteArray {
        val responseBody = api.getImageById(id)
        val outputStream = ByteArrayOutputStream()
        responseBody.byteStream().use { input ->
            val buffer = ByteArray(4096)
            var bytesRead: Int
            while (input.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
        }
        return outputStream.toByteArray()
    }
}