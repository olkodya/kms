package com.example.kms.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Operation (
    @SerialName("operation_id")
    val operation_id: Int,
//    @SerialName("key")
//    val key: Key,
//    @SerialName("employee")
//    val employee: Employee?,
//    @SerialName("shift")
//    val shift: Shift,
//    @SerialName("give_date_time")
//    val give_date_time: String?,
//    @SerialName("return_date_time")
//    val return_date_time: String?,
)