package com.example.kms.network.api

import com.example.kms.model.Audience
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

interface AudienceApi {
    @GET("/api/audiences")
    suspend fun getAudiences(): List<Audience>

    @GET("api/audiences/{id}")
    suspend fun getAudienceById(@Path("id") id: Int): Audience

    companion object {
        val INSTANCE: AudienceApi by lazy {
            RetrofitFactory.INSTANCE.create()
        }
    }

}