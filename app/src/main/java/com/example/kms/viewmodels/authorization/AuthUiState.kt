package com.example.kms.viewmodels.authorization

import com.example.kms.model.TokenDto
import com.example.kms.repository.Status

data class AuthUiState(
    val status: Status = Status.Idle,
    val token: TokenDto? = null,
    val login: String? = null,
    val password: String? = null,

    ) {
    val isSuccess: Boolean = status == Status.Idle && token != null
    val err: Throwable? = (status as? Status.Error)?.reason
}