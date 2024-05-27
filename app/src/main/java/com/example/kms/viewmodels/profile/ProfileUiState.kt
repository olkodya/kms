package com.example.kms.viewmodels.profile

import com.example.kms.model.DTO.ShiftUIModel
import com.example.kms.model.DTO.Watch
import com.example.kms.utils.Status

data class ProfileUiState (
    val shift: ShiftUIModel? = null,
    val status: Status = Status.Idle,
    val image: ByteArray? = null,
    val watches: List<Watch> = emptyList(),
)