package com.example.kms.model.DTO

import com.example.kms.model.enums.KeyState
import kotlinx.serialization.Serializable

@Serializable
data class KeyForm(
    val audience_id: Int,
    val key_state: KeyState,
    val main: Boolean,
)