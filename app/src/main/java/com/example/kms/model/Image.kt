package com.example.kms.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Image(
    @SerialName("image_id")
    val image_id: Int,
    @SerialName("name")
    val name: String?,
    @SerialName("type")
    val type: String?,
    //val data:
)