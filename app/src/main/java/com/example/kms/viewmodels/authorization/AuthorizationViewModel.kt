package com.example.kms.viewmodels.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kms.repository.repos.UserRepository
import com.example.kms.utils.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthorizationViewModel(private val repository: UserRepository) : ViewModel() {
    private val _state = MutableStateFlow(AuthUiState())
    val state = _state.asStateFlow()


    fun login(username: String, password: String) {
        _state.update { it.copy(status = Status.Loading) }
        viewModelScope.launch {
            try {
                val token = repository.login(username = username, password = password)
                _state.update {
                    it.copy(status = Status.Idle, token = token)
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(status = Status.Error(e))
                }
            }
        }
    }

    fun logout() {
        _state.update {
            it.copy(status = Status.Idle, token = null)
        }
    }

    fun getFromLocal() {

    }
}