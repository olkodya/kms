package com.example.kms.repository

import com.example.kms.model.Employee

interface EmployeeRepository {
    suspend fun getByQR(QR: String): Employee
    suspend fun getById(id: Int): Employee
}