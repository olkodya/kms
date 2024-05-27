package com.example.kms.model.DTO

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignatureForm(
    @SerialName("image_id")
    val image_id: Int,
    @SerialName("operation_id")
    val operation_id: Int,
    @SerialName("give")
    val give: Boolean
)