package com.example.kms.repository

import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ImageRepository {
    suspend fun getById(id: Int): ByteArray

    suspend fun upload(imageForm: RequestBody, file: MultipartBody.Part)
}