package com.example.kms.viewmodels.profile

import com.example.kms.model.Operation
import com.example.kms.model.Signature

data class RegisterItemUiState(
    val operation: Operation? = null,
    val employeePhoto: ByteArray? = null,
    val audiencePhoto: ByteArray? = null,
    val signatures: List<Signature> = emptyList(),
    val giveSignature: ByteArray? = null,
    val returnSignature: ByteArray? = null,
)