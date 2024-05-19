package com.example.kms.viewmodels.operations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kms.model.Employee
import com.example.kms.model.Key
import com.example.kms.model.Operation
import com.example.kms.repository.EmployeeRepository
import com.example.kms.repository.KeyRepository
import com.example.kms.repository.OperationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OperationsViewModel(
    private val repository: EmployeeRepository,
    private val keyRepository: KeyRepository,
    private val operationRepository: OperationRepository
) : ViewModel() {
    private val _employee = MutableStateFlow(true)
    val employee = _employee.asStateFlow()
    private val _scanned = MutableStateFlow(false)
    val scanned = _scanned.asStateFlow()
    private val _uiState = MutableStateFlow(OperationsUIState())
    val uiState = _uiState.asStateFlow()
    fun checkEmployee() {
        _employee.value = true
    }


    fun checkKey() {
        _employee.value = false
    }

    fun setScanned() {
        _scanned.value = true
    }

    fun unsetScanned() {
        _scanned.value = false
    }

    fun getEmployee(QR: String) {
        viewModelScope.launch {
            try {
                val employee: Employee = repository.getByQR(QR)
                _uiState.update {
                    it.copy(employee = employee)
                }
            } catch (e: Exception) {

            }
        }
    }

    fun getKey(QR: String) {
        viewModelScope.launch {
            try {
                val key: Key = keyRepository.getByQR(QR)
                _uiState.update {
                    it.copy(key = key)
                }
                if (!_employee.value) {
                    val operation: Operation = operationRepository.getLastOperation(key.key_id ?: 0)
                    _uiState.update {
                        it.copy(operation = operation, employee = operation.employee)
                    }
                }

//                val operation = operationRepository.getLastOperation(key.key_id?:0)
//                _uiState.update {
//                    it.copy(employee = operation.employee)
//                }
            } catch (e: Exception) {

            }
        }
    }


    fun reset() {
        _uiState.update {
            it.copy(
                key = null, employee = null, operation = null
            )
        }
    }

}