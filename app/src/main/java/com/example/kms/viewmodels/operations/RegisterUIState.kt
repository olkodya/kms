package com.example.kms.viewmodels.operations

import com.example.kms.model.Operation

data class RegisterUIState (
    val operations: List<Operation> = emptyList()
)