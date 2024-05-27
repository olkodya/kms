package com.example.kms.viewmodels.register

import com.example.kms.model.DTO.Operation
import com.example.kms.model.DTO.Signature

data class RegisterItemUiState(
    val operation: Operation? = null,
    val signatures: List<Signature> = emptyList(),
)