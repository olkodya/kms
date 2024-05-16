package com.example.kms.viewmodels.register

import com.example.kms.model.Audience

data class SignalisationRegisterUIState(
    val audiences: List<Audience> = emptyList(),
)
