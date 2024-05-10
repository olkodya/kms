package com.example.kms.model

import com.example.kms.model.enums.KeyState
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("key")
data class Key (
    @SerialName("key_id")
    val key_id: Int?,
//    @SerialName("audience")
//    val audience: Audience?,
    @SerialName("keyState")
    val keyState: KeyState?,
    @SerialName("main")
    val main: Boolean?,
    @SerialName("qr")
    val qr: String?
)