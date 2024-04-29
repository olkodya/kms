package com.example.kms.viewmodels.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kms.mapper.ShiftUiModelMapper
import com.example.kms.repository.ShiftRepository
import com.example.kms.repository.Status
import com.example.kms.repository.WatchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.truncate

class ProfileViewModel(
    private val repository: ShiftRepository,
    private val watchRepository: WatchRepository,
    private val mapper: ShiftUiModelMapper,
) : ViewModel() {
    private val _shiftStarted = MutableStateFlow(false)
    val shiftStarted = _shiftStarted.asStateFlow()
    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow()
    private val _logout = MutableStateFlow(false)
    val logout = _logout.asStateFlow()

    init {
        getWatches()
    }
    fun startShift(userId: Int, watchId: Int) {
        viewModelScope.launch {
            try {

                val shift = mapper.map(repository.createShift(userId = userId, watchId = watchId))
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
                val shift = mapper.map(repository.finishShift(id = _state.value.shift?.shift_id ?: 0))
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
                val shift1 = repository.getShift(userId)
                Log.d("AAAA", shift1.start_date_time.toString())
                val shift = mapper.map(shift1)
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

    fun logout() {
        _logout.value = true
    }
    fun login() {
        _logout.value = false
    }

}