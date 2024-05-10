package com.example.kms.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kms.model.Operation
import com.example.kms.model.OperationForm
import com.example.kms.repository.OperationRepository
import kotlinx.coroutines.launch

class SignaturePadViewModel(
    private val repository: OperationRepository
) : ViewModel() {
    fun createOperation(operationForm: OperationForm) {
        viewModelScope.launch {
            try {
                val operation: Operation = repository.createOperation(operationForm)

            } catch (e: Exception) {

            }
        }
    }
}