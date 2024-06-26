package com.example.kms.repository.reposImpl

import com.example.kms.model.DTO.Employee
import com.example.kms.model.DTO.EmployeeId
import com.example.kms.network.api.EmployeeApi
import com.example.kms.repository.repos.EmployeeRepository

class EmployeeRepositoryImpl(
    private val api: EmployeeApi
) : EmployeeRepository {
    override suspend fun getByQR(QR: String): Employee {
        return api.getEmployeeByQR(QR)
    }

    override suspend fun getById(id: Int): Employee {
        return api.getEmployeeById(id)
    }

    override suspend fun getEmployeeIdByEmployeeId(id: Int): EmployeeId {
        return api.getEmployeeIdByEmployeeId(id)
    }
}