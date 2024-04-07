package com.example.kms.viewmodels.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kms.repository.ShiftRepository
import com.example.kms.repository.Status
import com.example.kms.repository.WatchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: ShiftRepository,
    private val watchRepository: WatchRepository
) : ViewModel() {
    private val _shiftStarted = MutableStateFlow(false)
    val shiftStarted = _shiftStarted.asStateFlow()
    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow()

    init {
        getWatches()
    }
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

    fun finishShift() {
        viewModelScope.launch {
            try {
                val shift = repository.finishShift(id = _state.value.shift?.shift_id ?: 0)
                _state.update {
                    it.copy(status = Status.Idle, shift = shift)
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(status = Status.Error(e))
                }
            }
            _shiftStarted.update {
                false
            }
        }

    }


    fun getShift(userId: Int) {
        viewModelScope.launch {
            try {
                val shift = repository.getShift(userId)
                _state.update {
                    it.copy(status = Status.Idle, shift = shift)
                }
                if (shift.end_date_time == null) {
                    _shiftStarted.update {
                        true
                    }
                } else {
                    _shiftStarted.update {
                        false
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(status = Status.Error(e))
                }
            }

        }
    }

    private fun getWatches() {
        viewModelScope.launch {
            try {
                val watches = watchRepository.getWatches()
                _state.update {
                    it.copy(status = Status.Idle, watches = watches)
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(status = Status.Error(e))
                }

            }
        }
    }
}