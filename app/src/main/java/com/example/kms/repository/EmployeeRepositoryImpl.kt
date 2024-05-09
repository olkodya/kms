package com.example.kms.repository

import com.example.kms.model.Employee
import com.example.kms.network.api.EmployeeApi

class EmployeeRepositoryImpl(
    private val api: EmployeeApi
) : EmployeeRepository {
    override suspend fun getByQR(QR: String): Employee {
        return api.getOperation(QR)
    }

}