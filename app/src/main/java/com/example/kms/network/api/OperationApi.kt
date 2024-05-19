package com.example.kms.network.api

import com.example.kms.model.Operation
import com.example.kms.model.OperationForm
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface OperationApi {
    @GET("/api/operations")
    suspend fun getOperations(): List<Operation>

    @POST("/api/operations")
    suspend fun createOperation(@Body operationForm: OperationForm): Operation

    @GET("/api/operations/{id}")
    suspend fun getOperation(@Path("id") id: Int): Operation

    @GET("/api/keys/{id}/operations")
    suspend fun getLastOperation(@Path("id") keyId: Int): Operation

    @PUT("/api/operations/{id}")
    suspend fun finishOperation(@Path("id") id: Int): Operation


    companion object {
        val INSTANCE: OperationApi by lazy {
            RetrofitFactory.INSTANCE.create()
        }
    }
}