package com.example.kms.viewmodels.profile

import com.example.kms.model.EmployeeId
import com.example.kms.model.Operation
import com.example.kms.model.Signature

data class RegisterItemUiState(
    val operation: Operation? = null,
    val signatures: List<Signature> = emptyList(),
    val employeeId: EmployeeId? = null,
)