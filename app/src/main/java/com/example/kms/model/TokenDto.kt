package com.example.kms.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenDto(
    @SerialName("access_token")
    val token: String,
    @SerialName("username")
    val username: String,
    @SerialName("user_id")
    val user_id: Int,
    @SerialName("employee")
    val employee: Employee,
)
