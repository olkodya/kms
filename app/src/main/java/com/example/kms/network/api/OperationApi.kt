package com.example.kms.network.api

import com.example.kms.model.Operation
import com.example.kms.model.Watch
import retrofit2.create
import retrofit2.http.GET

interface OperationApi {
    @GET("/api/operations")
    suspend fun getOperations(): List<Operation>
    companion object {
        val INSTANCE: OperationApi by lazy {
            RetrofitFactory.INSTANCE.create()
        }
    }
}