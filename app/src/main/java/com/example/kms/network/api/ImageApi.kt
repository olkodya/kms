package com.example.kms.network.api

import okhttp3.ResponseBody
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageApi {
    @GET("api/images/{id}")
    suspend fun getImageById(@Path("id") id: Int): ResponseBody

    companion object {
        val INSTANCE: ImageApi by lazy {
            RetrofitFactory.INSTANCE.create()
        }
    }
}