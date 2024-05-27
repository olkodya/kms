package com.example.kms.model.DTO

import com.example.kms.model.enums.EmployeeType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Employee(
    @SerialName("employeeId")
    val employeeId: Int,
    @SerialName("firstName")
    val firstName: String,
    @SerialName("secondName")
    val secondName: String,
    @SerialName("middleName")
    val middleName: String,
    @SerialName("image")
    val image: Image?,
    @SerialName("employeeType")
    val employeeType: EmployeeType?,
    @SerialName("employeeStatus")
    val employeeStatus: String?,
    @SerialName("divisions")
    val divisions: List<Division>?,
    @SerialName("permissions")
    val permissions: List<Permission?>?,
    @SerialName("qr")
    val qr: String?
)