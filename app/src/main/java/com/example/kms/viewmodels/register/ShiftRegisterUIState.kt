package com.example.kms.viewmodels.register

import com.example.kms.model.Operation

data class ShiftRegisterUIState(
    val operations: List<Operation> = emptyList(),
)