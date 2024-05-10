package com.example.kms.repository

import com.example.kms.model.TokenDto

interface UserRepository {
    suspend fun login(username: String, password: String): TokenDto

}