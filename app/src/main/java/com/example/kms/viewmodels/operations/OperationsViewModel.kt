package com.example.kms.viewmodels.operations

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OperationsViewModel(): ViewModel() {
    private val _giveKey = MutableStateFlow(true)
    val giveKey = _giveKey.asStateFlow()



    fun checkGive() {
        _giveKey.value = true
    }


    fun checkTake() {
        _giveKey.value = false
    }
}