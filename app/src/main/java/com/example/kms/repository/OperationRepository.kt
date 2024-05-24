package com.example.kms.repository

import com.example.kms.model.Operation
import com.example.kms.model.OperationForm
import com.example.kms.model.Signature

interface OperationRepository {
    suspend fun finishOperation(operationId: Int): Operation
    suspend fun getAll(): List<Operation>
    suspend fun getById(id: Int): Operation

    suspend fun getLastOperation(id: Int): Operation
    suspend fun createOperation(operationForm: OperationForm): Operation

    suspend fun getSignatures(id: Int): List<Signature>

}