package com.example.kms.network.api

import com.example.kms.model.DTO.Shift
import com.example.kms.network.factories.RetrofitFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ShiftApi {
    @POST("/api/shifts")
    suspend fun createShift(@Query("userId") userId: Int, @Query("watchId") watchId: Int) : Shift
    @GET("/api/users/{userId}/shift")
    suspend fun getShift(@Path("userId") userId: Int): Shift

    @PUT("api/shifts/{id}")
    suspend fun finishShift(@Path("id") id: Int): Shift
    companion object {
        val INSTANCE: ShiftApi by lazy {
            RetrofitFactory.INSTANCE.create()
        }
    }
}