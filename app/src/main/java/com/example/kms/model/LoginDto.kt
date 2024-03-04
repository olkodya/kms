package com.example.kms.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginDto (
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String
)