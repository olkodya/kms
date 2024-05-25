package com.example.kms.viewmodels.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kms.repository.EmployeeRepository
import com.example.kms.repository.ImageRepository
import com.example.kms.repository.OperationRepository
import com.example.kms.viewmodels.profile.RegisterItemUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterItemViewModel(
    private val repository: OperationRepository,
    private val imageRepository: ImageRepository,
    private val employeeRepository: EmployeeRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterItemUiState())
    val uiState = _uiState.asStateFlow()
    private val _giveSignature = MutableStateFlow<ByteArray?>(ByteArray(0))
    val giveSignature = _giveSignature.asStateFlow()
    private val _returnSignature = MutableStateFlow<ByteArray?>(ByteArray(0))
    val returnSignature = _returnSignature.asStateFlow()
    private val _employeePhoto = MutableStateFlow<ByteArray?>(ByteArray(0))
    val employeePhoto = _employeePhoto.asStateFlow()
    val _audiencePhoto = MutableStateFlow<ByteArray?>(ByteArray(0))
    val audiencePhoto = _audiencePhoto.asStateFlow()


    fun getOperation(id: Int) {
        viewModelScope.launch {
            try {
                val operation = repository.getById(id)
                _uiState.update {
                    it.copy(operation = operation)
                }
                getSignatures(operation.operation_id)
                getEmployeePhoto(operation.employee?.image?.image_id ?: 103)
                getAudiencePhoto(operation.key.audience.image?.image_id ?: 103)
                getEmployeeID(operation.employee?.employee_id ?: 0)
            } catch (ex: Exception) {


            }
        }
    }

    fun getEmployeePhoto(id: Int) {
        viewModelScope.launch {
            try {
                val employeePhoto = imageRepository.getById(id)
                _employeePhoto.update {
                    employeePhoto
                }
            } catch (ex: Exception) {


            }
        }
    }

    fun getAudiencePhoto(id: Int) {
        viewModelScope.launch {
            try {
                val audiencePhoto = imageRepository.getById(id)
                _audiencePhoto.update {
                    audiencePhoto
                }
            } catch (ex: Exception) {


            }
        }
    }

    fun getEmployeeID(id: Int) {
        viewModelScope.launch {
            try {
                val employeeID = employeeRepository.getEmployeeIdByEmployeeId(id)
                _uiState.update {
                    it.copy(employeeId = employeeID)
                }
            } catch (ex: Exception) {


            }
        }
    }

    fun getSignatures(id: Int) {
        viewModelScope.launch {
            try {
                val signatures = repository.getSignatures(id)
                _uiState.update {
                    it.copy(signatures = signatures)
                }
                if (signatures.isNotEmpty()) {
                    val giveSignature = imageRepository.getById(signatures[0].image.image_id)
                    _giveSignature.update {
                        giveSignature
                    }
                    if (signatures.size > 1) {
                        val returnSignature = imageRepository.getById(signatures[1].image.image_id)
                        _returnSignature.update {
                            returnSignature
                        }
                    }
                }
            } catch (ex: Exception) {


            }
        }
    }


}