package com.example.kms.model.DTO

import kotlinx.serialization.Serializable

@Serializable
data class Signature(
    val give: Boolean,
    val image: Image,
)