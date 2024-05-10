package com.example.kms.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Division(
    @SerialName("division_id")
    val division_id: Int,
    val name: String?,
    val employees: List<Employee?>?,
    val permissions: List<Permission?>?,
)