package com.example.kms.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Employee (
    @SerialName("employee_id")
    val employee_id: Int,
    @SerialName("first_name")
    val first_name: String,
    @SerialName("second_name")
    val second_name: String,
    @SerialName("middle_name")
    val middle_name: String,
//    @SerialName("photo_url")
//    val photo_url: String,
//    @SerialName("employeeType")
//    val employeeType: ,
)