package com.example.kms.viewmodels.operations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kms.model.Employee
import com.example.kms.repository.EmployeeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OperationsViewModel(private val repository: EmployeeRepository): ViewModel() {
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

    fun giveKey(QR: String) {
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

}