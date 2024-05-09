package com.example.kms.network.api

import com.example.kms.model.Operation
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

interface OperationApi {
    @GET("/api/operations")
    suspend fun getOperations(): List<Operation>


    @GET("api/operations/{id}")
    suspend fun getOperation(@Path("id") id: Int): Operation
    companion object {
        val INSTANCE: OperationApi by lazy {
            RetrofitFactory.INSTANCE.create()
        }
    }
}