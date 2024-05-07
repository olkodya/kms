package com.example.kms.repository

import com.example.kms.model.Employee
import com.example.kms.model.Key
import com.example.kms.model.Operation

interface OperationRepository {
    suspend fun createOperation(keyId: Int, employeeId: Int, shiftId: Int): Operation
    suspend fun finishOperation(operationId: Int): Operation
    suspend fun getAll(): List<Operation>
    suspend fun getById(id: Int): Operation


}