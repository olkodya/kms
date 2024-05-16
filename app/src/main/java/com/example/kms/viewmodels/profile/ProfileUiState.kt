package com.example.kms.viewmodels.profile

import com.example.kms.model.ShiftUIModel
import com.example.kms.model.Watch
import com.example.kms.repository.Status

data class ProfileUiState (
    val shift: ShiftUIModel? = null,
    val status: Status = Status.Idle,
    val image: String? = null,
    val watches: List<Watch> = emptyList(),
    )