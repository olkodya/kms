package com.example.kms.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kms.repository.AudienceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AudienceInfoViewModel(val repository: AudienceRepository) : ViewModel() {
    private val _audience = MutableStateFlow(AudienceInfoUIState())
    val audience = _audience.asStateFlow()
    fun getByID(id: Int) {
        viewModelScope.launch {
            try {
                val audience = repository.getByID(id)
                _audience.update {
                    it.copy(audience = audience)
                }
            } catch (ex: Exception) {


            }
        }
    }
}