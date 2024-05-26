package com.example.kms.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Watch(
    @SerialName("watchId")
    val watchId: Int,
    @SerialName("buildingNumber")
    val buildingNumber: Int?,
)