package com.example.kms.repository.repos

import com.example.kms.model.DTO.TokenDto

interface UserRepository {
    suspend fun login(username: String, password: String): TokenDto

}