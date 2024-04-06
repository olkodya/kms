package com.example.kms.viewmodels.authorization

import com.example.kms.model.TokenDto
import com.example.kms.repository.Status

data class AuthUiState (
    val status: Status = Status.Idle,
    val token: TokenDto? = null,
) {
    val isSuccess: Boolean = status == Status.Idle && token != null
}