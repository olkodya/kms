package com.example.kms.model.DTO

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmployeeId(
    @SerialName("id")
    val id: Int,
    @SerialName("number")
    val number: String,
//    @SerialName("not_expired")
//    val not_expired: Boolean,
)