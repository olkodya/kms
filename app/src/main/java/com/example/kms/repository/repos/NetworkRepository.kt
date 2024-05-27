package com.example.kms.repository.repos

import com.example.kms.model.DTO.LoginDto
import com.example.kms.model.DTO.TokenDto
import com.example.kms.network.api.UserApi

class NetworkUserRepository(
    private val api: UserApi
) : UserRepository {
    override suspend fun login(username: String, password: String): TokenDto {
        return api.getLogin(LoginDto(username = username, password = password))
    }
}