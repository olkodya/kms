package com.example.kms.viewmodels.register

import com.example.kms.model.DTO.Audience

data class SignalisationRegisterUIState(
    val audiences: List<Audience> = emptyList(),
)
