package com.example.kms.network.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit


object RetrofitFactory {
    private val JSON_TYPE = "application/json".toMediaType()
    private val JSON = Json {
        ignoreUnknownKeys = true
    }

    val INSTANCE: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://muddled-event-production.up.railway.app")
            .client(OkHttpFactory.INSTANCE)
            .addConverterFactory(JSON.asConverterFactory(JSON_TYPE))
            .build()
    }
}