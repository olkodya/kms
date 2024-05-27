package com.example.kms.repository.repos

import com.example.kms.model.DTO.Image
import com.example.kms.model.DTO.Signature
import com.example.kms.model.DTO.SignatureForm
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ImageRepository {
    suspend fun getById(id: Int): ByteArray

    suspend fun upload(imageForm: RequestBody, file: MultipartBody.Part): Image

    suspend fun createSignature(signatureForm: SignatureForm): Signature
}