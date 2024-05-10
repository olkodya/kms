package com.example.kms.model.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AudienceType {
    @SerialName("STUDY")
    STUDY,

    @SerialName("MULTIMEDIA")
    MULTIMEDIA,

    @SerialName("LAB")
    LAB,

    @SerialName("ADMINISTRATION")
    ADMINISTRATION
}