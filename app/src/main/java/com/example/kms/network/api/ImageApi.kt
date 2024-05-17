package com.example.kms.network.api

import com.example.kms.model.Image
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ImageApi {
    @GET("api/images/{id}")
    suspend fun getImageById(@Path("id") id: Int): ResponseBody

    @Multipart
    @POST("api/images")
    suspend fun uploadImage(
        @Part("image") image: RequestBody,
        @Part file: MultipartBody.Part
    ): Image

    companion object {
        val INSTANCE: ImageApi by lazy {
            RetrofitFactory.INSTANCE.create()
        }
    }
}