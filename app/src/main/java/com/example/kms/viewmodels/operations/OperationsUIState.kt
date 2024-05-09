package com.example.kms.viewmodels.operations

import com.example.kms.model.Employee
import com.example.kms.model.ShiftUIModel
import com.example.kms.model.Watch
import com.example.kms.repository.Status

data class OperationsUIState (
    val status: Status = Status.Idle,
    val employee: Employee? = null,
) {
    val isSuccess: Boolean = status == Status.Idle && employee != null
}