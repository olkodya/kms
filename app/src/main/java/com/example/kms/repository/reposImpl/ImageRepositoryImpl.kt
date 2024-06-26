package com.example.kms.repository.reposImpl

import com.example.kms.model.DTO.Image
import com.example.kms.model.DTO.Signature
import com.example.kms.model.DTO.SignatureForm
import com.example.kms.network.api.ImageApi
import com.example.kms.repository.repos.ImageRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    override suspend fun upload(imageForm: RequestBody, file: MultipartBody.Part): Image {
        return api.uploadImage(imageForm, file)
    }

    override suspend fun createSignature(signatureForm: SignatureForm): Signature {
        return api.createSignature(signatureForm)
    }
}