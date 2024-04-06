package com.example.kms.viewmodels.profile

import com.example.kms.model.Shift
import com.example.kms.repository.Status

data class ProfileUiState (
    val shift: Shift? = null,
    val status: Status = Status.Idle,
    )