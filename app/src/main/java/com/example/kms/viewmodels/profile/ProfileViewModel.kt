package com.example.kms.viewmodels.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kms.model.Shift
import com.example.kms.repository.ShiftRepository
import com.example.kms.repository.Status

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: ShiftRepository) : ViewModel() {
    private val _shiftStarted = MutableStateFlow(false)
    val shiftStarted = _shiftStarted.asStateFlow()
    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow()



       fun startShift(userId: Int, watchId: Int) {
        viewModelScope.launch {
            try {
                val shift = repository.createShift(userId = userId, watchId = watchId)
                _state.update {
                    it.copy(status = Status.Idle, shift = shift)
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(status = Status.Error(e))
                }
            }
            _shiftStarted.update {
                true
            }
        }
    }

    fun finishShift(id: Int) {
        viewModelScope.launch {
            try {
                val shift = repository.finishShift(id = id)
                _state.update {
                    it.copy(status = Status.Idle, shift = shift)
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(status = Status.Error(e))
                }
            }
            _shiftStarted.update{
                false
            }
        }

    }

    fun getShift(userId: Int) {
        var shift: Shift? = null
        viewModelScope.launch {
            try {
               shift = repository.getShift(userId)
                _state.update {
                    it.copy(status = Status.Idle, shift = shift)
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(status = Status.Error(e))
                }
            }

            if(shift?.end_date_time.isNullOrEmpty()){
                _shiftStarted.update {
                    true
                }
            } else {
                _shiftStarted.update {
                    false
                }
            }

        }

    }
}