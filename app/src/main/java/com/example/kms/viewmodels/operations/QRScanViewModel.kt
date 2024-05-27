package com.example.kms.viewmodels.operations

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class QRScanViewModel(
) : ViewModel() {
    private val _employee = MutableStateFlow(true)
    val employee = _employee.asStateFlow()


}