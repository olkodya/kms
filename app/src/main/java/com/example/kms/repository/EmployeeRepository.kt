package com.example.kms.repository

import com.example.kms.model.Employee
import com.example.kms.model.EmployeeId

interface EmployeeRepository {
    suspend fun getByQR(QR: String): Employee
    suspend fun getById(id: Int): Employee
    suspend fun getEmployeeIdByEmployeeId(id: Int): EmployeeId
}