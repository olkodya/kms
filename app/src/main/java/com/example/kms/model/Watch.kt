package com.example.kms.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Watch(
    @SerialName("watch_id")
    val watch_id: Int,
    @SerialName("building_number")
    val building_number: Int?,
)