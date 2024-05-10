package com.example.kms.model

import kotlinx.serialization.Serializable

@Serializable
data class OperationForm(
    val key_id: Int,
    val employee_id: Int,
    val shift_id: Int,
)