package com.example.kms.network.api

import com.example.kms.model.DTO.LoginDto
import com.example.kms.model.DTO.TokenDto
import com.example.kms.network.factories.RetrofitFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/api/users/auth")
    suspend fun getLogin(@Body loginDto: LoginDto): TokenDto
    companion object {
        val INSTANCE: UserApi by lazy {
            RetrofitFactory.INSTANCE.create()
        }
    }
}