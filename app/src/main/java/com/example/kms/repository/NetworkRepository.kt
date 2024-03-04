package com.example.kms.repository

import com.example.kms.model.LoginDto
import com.example.kms.model.TokenDto
import com.example.kms.network.api.UserApi

class NetworkUserRepository(
    private val api: UserApi
) : UserRepository {
    override suspend fun login(username: String, password:String): TokenDto {
        return api.getLogin(LoginDto(username = username, password = password))
    }
}