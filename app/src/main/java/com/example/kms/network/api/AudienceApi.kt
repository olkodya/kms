package com.example.kms.network.api

import com.example.kms.model.Audience
import retrofit2.create
import retrofit2.http.GET

interface AudienceApi {
    @GET("/api/audiences")
    suspend fun getAudiences(): List<Audience>

    companion object {
        val INSTANCE: AudienceApi by lazy {
            RetrofitFactory.INSTANCE.create()
        }
    }

}