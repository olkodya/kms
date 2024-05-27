package com.example.kms.viewmodels.register

import com.example.kms.model.DTO.Operation

data class ShiftRegisterUIState(
    val operations: List<Operation> = emptyList(),
)