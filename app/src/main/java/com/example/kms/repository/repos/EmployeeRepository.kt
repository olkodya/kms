package com.example.kms.repository.repos

import com.example.kms.model.DTO.Employee
import com.example.kms.model.DTO.EmployeeId

interface EmployeeRepository {
    suspend fun getByQR(QR: String): Employee
    suspend fun getById(id: Int): Employee
    suspend fun getEmployeeIdByEmployeeId(id: Int): EmployeeId
}