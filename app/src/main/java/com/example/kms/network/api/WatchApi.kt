package com.example.kms.network.api

import com.example.kms.model.LoginDto
import com.example.kms.model.TokenDto
import com.example.kms.model.Watch
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
    interface WatchApi {
        @GET("/api/watches")
        suspend fun getWatches(): List<Watch>
        companion object {
            val INSTANCE: WatchApi by lazy {
                RetrofitFactory.INSTANCE.create()
            }
        }
    }
