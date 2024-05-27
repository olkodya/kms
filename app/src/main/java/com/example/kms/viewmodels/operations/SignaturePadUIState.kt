package com.example.kms.viewmodels.operations

import com.example.kms.model.DTO.Employee
import com.example.kms.model.DTO.Key
import com.example.kms.model.DTO.Operation

data class SignaturePadUIState(
    val operation: Operation? = null,
    val employee: Employee? = null,
    val key: Key? = null
)