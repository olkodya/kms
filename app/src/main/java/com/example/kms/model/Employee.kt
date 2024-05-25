package com.example.kms.model

import com.example.kms.model.enums.EmployeeType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Employee(
    @SerialName("employee_id")
    val employee_id: Int,
    @SerialName("first_name")
    val first_name: String,
    @SerialName("second_name")
    val second_name: String,
    @SerialName("middle_name")
    val middle_name: String,
    @SerialName("image")
    val image: Image?,
    @SerialName("employee_type")
    val employee_type: EmployeeType?,
    @SerialName("employee_status")
    val employee_status: String?,
    @SerialName("divisions")
    val divisions: List<Division>?,
    @SerialName("permissions")
    val permissions: List<Permission?>?,
    @SerialName("qr")
    val qr: String?
)