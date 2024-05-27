package com.example.kms.repository.repos

import com.example.kms.model.DTO.Operation
import com.example.kms.model.DTO.OperationForm
import com.example.kms.model.DTO.Signature

interface OperationRepository {
    suspend fun finishOperation(operationId: Int): Operation
    suspend fun getAll(): List<Operation>
    suspend fun getById(id: Int): Operation

    suspend fun getLastOperation(id: Int): Operation
    suspend fun createOperation(operationForm: OperationForm): Operation

    suspend fun getSignatures(id: Int): List<Signature>

}