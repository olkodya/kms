package com.example.kms.viewmodels.operations

import com.example.kms.model.Employee
import com.example.kms.model.Key
import com.example.kms.repository.Status

data class OperationsUIState(
    val status: Status = Status.Idle,
    val employee: Employee? = null,
    val key: Key? = null
) {
    val isSuccess: Boolean = status == Status.Idle && (employee != null || key != null)
}