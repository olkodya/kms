package com.example.kms.viewmodels

import com.example.kms.model.Employee
import com.example.kms.model.EmployeeId

data class EmployeeInfoUIState(
    val employee: Employee? = null,
    val employeeId: EmployeeId? = null
)