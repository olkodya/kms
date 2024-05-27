package com.example.kms.network.api

import com.example.kms.model.DTO.Employee
import com.example.kms.model.DTO.EmployeeId
import com.example.kms.network.factories.RetrofitFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

interface EmployeeApi {
    @GET("api/QRs/{QR}/employees")
    suspend fun getEmployeeByQR(@Path("QR") QR: String): Employee

    @GET("api/employees/{id}")
    suspend fun getEmployeeById(@Path("id") QR: Int): Employee

    @GET("api/employees/{id}/employeeIds")
    suspend fun getEmployeeIdByEmployeeId(@Path("id") id: Int): EmployeeId
    companion object {
        val INSTANCE: EmployeeApi by lazy {
            RetrofitFactory.INSTANCE.create()
        }
    }
}