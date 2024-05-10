package com.example.kms.repository

import com.example.kms.model.Operation
import com.example.kms.model.OperationForm
import com.example.kms.network.api.OperationApi

class OperationsRepositoryImpl(
    private val api: OperationApi
) : OperationRepository {
    override suspend fun createOperation(keyId: Int, employeeId: Int, shiftId: Int): Operation {
        TODO("Not yet implemented")
    }

    override suspend fun createOperation(operationForm: OperationForm): Operation =
        api.createOperation(operationForm)

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