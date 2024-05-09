package com.example.kms.network.api

import com.example.kms.model.Employee
import com.example.kms.model.Operation
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

interface EmployeeApi {
    @GET("api/QRs/{QR}/employees")
    suspend fun getOperation(@Path("QR") QR: String): Employee
    companion object {
        val INSTANCE: EmployeeApi by lazy {
            RetrofitFactory.INSTANCE.create()
        }
    }
}