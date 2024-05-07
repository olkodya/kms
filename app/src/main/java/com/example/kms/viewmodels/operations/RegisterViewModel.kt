package com.example.kms.viewmodels.operations

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kms.model.Employee
import com.example.kms.model.Key
import com.example.kms.model.LoginDto
import com.example.kms.model.Operation
import com.example.kms.model.Shift
import com.example.kms.model.Watch
import com.example.kms.repository.OperationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: OperationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUIState())
    val uiState: StateFlow<RegisterUIState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            try {
                val operations: List<Operation> = repository.getAll()
                _uiState.update {
                    it.copy(operations = operations)
                }
            } catch (e: Exception) {

            }
        }

    }
}