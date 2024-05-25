package com.example.kms.viewmodels.operations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kms.model.Employee
import com.example.kms.model.Key
import com.example.kms.model.Operation
import com.example.kms.model.Permission
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

    fun getKey(QR: String, id: Int?) {
        var hasPermission: Boolean = false
        viewModelScope.launch {
            try {
                val key: Key = keyRepository.getByQR(QR)
                if (!_employee.value) {
                    val operation: Operation = operationRepository.getLastOperation(key.key_id ?: 0)
                    _uiState.update {
                        it.copy(operation = operation, employee = operation.employee)
                    }
                } else {
                    if (id != null) {
                        val employee: Employee = repository.getById(id)
                        val employeePerm =
                            (employee.permissions ?: emptyList<Permission>()).toMutableList()
                        if (employee.divisions != null) {
                            for (division in employee.divisions) {
                                if (division.permissions != null) {
                                    for (perm in division.permissions) {
                                        employeePerm += perm
                                    }
                                }
                            }
                        }
                        if (employeePerm != emptyList<Permission>() && key.audience.permissions != null) {
                            for (employeePermission in employeePerm) {
                                if (key.audience.permissions.contains(employeePermission)) {
                                    hasPermission = true
                                }
                            }
                        }
                    }
                }

//                val operation = operationRepository.getLastOperation(key.key_id?:0)
//                _uiState.update {
//                    it.copy(employee = operation.employee)
//                }

                _uiState.update {
                    it.copy(key = key, hasPermission = hasPermission)
                }
            } catch (e: Exception) {

            }
        }
    }


    fun reset() {
        _uiState.update {
            it.copy(
                key = null, employee = null, operation = null, hasPermission = true
            )
        }
    }

}