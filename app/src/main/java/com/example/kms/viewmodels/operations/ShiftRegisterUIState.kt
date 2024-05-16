package com.example.kms.viewmodels.operations

import com.example.kms.model.Operation

data class ShiftRegisterUIState(
    val operations: List<Operation> = emptyList(),
)