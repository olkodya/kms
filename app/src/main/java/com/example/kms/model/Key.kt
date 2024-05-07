package com.example.kms.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Key (
    @SerialName("key_id")
    val key_id: Int,
//    @SerialName("audience")
//    val audience: Audience,
//    @SerialName("keyState")
//    val keyState: String, //TODO enum
//    @SerialName("main")
//    val main: Boolean,
)