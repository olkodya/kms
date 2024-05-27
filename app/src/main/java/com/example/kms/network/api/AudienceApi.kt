package com.example.kms.network.api

import com.example.kms.model.DTO.Audience
import com.example.kms.model.enums.AudienceForm
import com.example.kms.network.factories.RetrofitFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface AudienceApi {
    @GET("/api/audiences")
    suspend fun getAudiences(): List<Audience>

    @GET("api/audiences/{id}")
    suspend fun getAudienceById(@Path("id") id: Int): Audience

    @PUT("api/audiences/{id}")
    suspend fun updateAudience(@Path("id") id: Int, @Body audienceForm: AudienceForm): Audience

    companion object {
        val INSTANCE: AudienceApi by lazy {
            RetrofitFactory.INSTANCE.create()
        }
    }

}