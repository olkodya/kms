package com.example.kms.network.api

import com.example.kms.model.Key
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

interface KeyApi {
    @GET("api/QRs/{QR}/keys")
    suspend fun getKeyByQR(@Path("QR") QR: String): Key

    companion object {
        val INSTANCE: KeyApi by lazy {
            RetrofitFactory.INSTANCE.create()
        }
    }
}