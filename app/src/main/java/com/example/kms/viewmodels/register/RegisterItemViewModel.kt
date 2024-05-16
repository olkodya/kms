package com.example.kms.viewmodels.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kms.repository.OperationRepository
import com.example.kms.viewmodels.profile.RegisterItemUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterItemViewModel(private val repository: OperationRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterItemUiState())
    val uiState = _uiState.asStateFlow()

    fun getOperation(id: Int) {
        viewModelScope.launch {
            try {
                val operation = repository.getById(id)
                _uiState.update {
                    it.copy(operation = operation)
                }
            } catch (ex: Exception) {


            }
        }
    }

}