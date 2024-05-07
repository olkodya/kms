package com.example.kms.viewmodels.operations

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OperationsViewModel(): ViewModel() {
    private val _giveKey = MutableStateFlow(true)
    val giveKey = _giveKey.asStateFlow()
    private val _scanned = MutableStateFlow(false)
    val scanned = _scanned.asStateFlow()


    fun checkGive() {
        _giveKey.value = true
    }


    fun checkTake() {
        _giveKey.value = false
    }

    fun setScanned() {
        _scanned.value = true
    }

    fun unsetScanned() {
        _scanned.value = false
    }
}