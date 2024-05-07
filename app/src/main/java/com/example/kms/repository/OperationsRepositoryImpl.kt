package com.example.kms.repository

import com.example.kms.model.Operation
import com.example.kms.network.api.OperationApi
import com.example.kms.network.api.ShiftApi

class OperationsRepositoryImpl(
    private val api: OperationApi
): OperationRepository {
    override suspend fun createOperation(keyId: Int, employeeId: Int, shiftId: Int): Operation {
        TODO("Not yet implemented")
    }

    override suspend fun finishOperation(operationId: Int): Operation {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(): List<Operation> {
        return api.getOperations()
    }

    override suspend fun getById(id: Int): Operation {
        return api.getOperation(id)
    }
}