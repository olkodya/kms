package com.example.kms.model.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class SignalisationType {
    @SerialName("NONE")
    NONE,

    @SerialName("OFF")
    OFF,

    @SerialName("ON")
    ON
}