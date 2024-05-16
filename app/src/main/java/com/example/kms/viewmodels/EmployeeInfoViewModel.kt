package com.example.kms.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kms.repository.EmployeeRepository
import com.example.kms.repository.ImageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EmployeeInfoViewModel(
    private val repository: EmployeeRepository,
    private val imageRepository: ImageRepository
) : ViewModel() {
    private val _employee = MutableStateFlow(EmployeeInfoUIState())
    val employee = _employee.asStateFlow()

    fun getByID(id: Int) {
        viewModelScope.launch {
            try {
                val employee = repository.getById(id)
                _employee.update {
                    it.copy(employee = employee)
                }
            } catch (ex: Exception) {


            }
        }
    }

    fun getEmployeeIDById(id: Int) {
        viewModelScope.launch {
            try {
                val employeeId = repository.getEmployeeIdByEmployeeId(id)
                _employee.update {
                    it.copy(employeeId = employeeId)
                }
            } catch (ex: Exception) {


            }
        }
    }

    fun getEmployeePhoto(id: Int) {
        viewModelScope.launch {
            try {
                val employeePhoto = imageRepository.getById(id)
                _employee.update {
                    it.copy(employeePhoto = employeePhoto)
                }
            } catch (ex: Exception) {


            }
        }
    }

}