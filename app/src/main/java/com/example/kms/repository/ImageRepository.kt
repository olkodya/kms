package com.example.kms.repository

import com.example.kms.model.Image
import com.example.kms.model.Signature
import com.example.kms.model.SignatureForm
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ImageRepository {
    suspend fun getById(id: Int): ByteArray

    suspend fun upload(imageForm: RequestBody, file: MultipartBody.Part): Image

    suspend fun createSignature(signatureForm: SignatureForm): Signature
}