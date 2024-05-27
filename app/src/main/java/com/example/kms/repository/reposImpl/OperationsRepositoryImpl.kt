package com.example.kms.repository.reposImpl

import com.example.kms.model.DTO.Operation
import com.example.kms.model.DTO.OperationForm
import com.example.kms.model.DTO.Signature
import com.example.kms.network.api.OperationApi
import com.example.kms.repository.repos.OperationRepository

class OperationsRepositoryImpl(
    private val api: OperationApi
) : OperationRepository {


    override suspend fun createOperation(operationForm: OperationForm): Operation =
        api.createOperation(operationForm)

    override suspend fun getSignatures(id: Int): List<Signature> = api.getSignatures(id)
    override suspend fun finishOperation(operationId: Int): Operation =
        api.finishOperation(operationId)

    override suspend fun getAll(): List<Operation> {
        return api.getOperations()
    }

    override suspend fun getById(id: Int): Operation {
        return api.getOperation(id)
    }

    override suspend fun getLastOperation(id: Int): Operation = api.getLastOperation(id)
}