package com.example.kms.model.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class KeyState {
    @SerialName("GIVEN_AWAY")
    GIVEN_AWAY,

    @SerialName("RETURNED")
    RETURNED,

    @SerialName("LOST")
    LOST
}