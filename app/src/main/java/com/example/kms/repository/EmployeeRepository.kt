package com.example.kms.repository

import com.example.kms.model.Employee
import com.example.kms.model.Operation

interface EmployeeRepository {
    suspend fun getByQR(QR: String): Employee

}