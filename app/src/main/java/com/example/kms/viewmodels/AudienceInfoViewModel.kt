package com.example.kms.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kms.repository.AudienceRepository
import com.example.kms.repository.ImageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AudienceInfoViewModel(
    val repository: AudienceRepository,
    val imageRepository: ImageRepository
) : ViewModel() {
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


    fun getAudiencePhoto(id: Int) {
        viewModelScope.launch {
            try {
                val audiencePhoto = imageRepository.getById(id)
                _audience.update {
                    it.copy(audiencePhoto = audiencePhoto)
                }
            } catch (ex: Exception) {


            }
        }
    }
}