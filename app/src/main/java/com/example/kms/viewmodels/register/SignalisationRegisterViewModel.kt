package com.example.kms.viewmodels.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kms.model.Audience
import com.example.kms.repository.AudienceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignalisationRegisterViewModel(private val repository: AudienceRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(SignalisationRegisterUIState())
    val uiState: StateFlow<SignalisationRegisterUIState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            try {
                val audiences: List<Audience> = repository.getAudiences()
                _uiState.update {
                    it.copy(audiences = audiences)
                }
            } catch (e: Exception) {

            }
        }
    }
}