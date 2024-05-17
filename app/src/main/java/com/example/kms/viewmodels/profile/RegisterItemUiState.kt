package com.example.kms.viewmodels.profile

import com.example.kms.model.Operation

data class RegisterItemUiState(
    val operation: Operation? = null,
    val employeePhoto: ByteArray? = null,
    val audiencePhoto: ByteArray? = null
)