package com.example.kms.viewmodels.shared

import com.example.kms.model.DTO.Employee
import com.example.kms.model.DTO.EmployeeId

data class EmployeeInfoUIState(
    val employee: Employee? = null,
    val employeeId: EmployeeId? = null,
)