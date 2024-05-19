package com.example.kms.viewmodels

import com.example.kms.model.Employee
import com.example.kms.model.Key
import com.example.kms.model.Operation

data class SignaturePadUIState(
    val operation: Operation? = null,
    val employee: Employee? = null,
    val key: Key? = null
)