package com.example.kms.model.enums

import kotlinx.serialization.Serializable

@Serializable
data class AudienceForm(
    val number: Int,
    val capacity: Int,
    val exist: Boolean,
    val signalisation: SignalisationType,
    val audience_type: AudienceType,
    val image_id: Int?
)