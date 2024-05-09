package com.example.kms.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kms.mapper.ShiftUiModelMapper
import com.example.kms.repository.ShiftRepository
import com.example.kms.repository.Status
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QRScanViewModel(
    ) : ViewModel() {
    private val _employee = MutableStateFlow(true)
    val employee = _employee.asStateFlow()



}