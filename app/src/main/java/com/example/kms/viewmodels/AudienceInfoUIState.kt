package com.example.kms.viewmodels

import com.example.kms.model.Audience

data class AudienceInfoUIState(
    val audience: Audience? = null,
    val audiencePhoto: ByteArray? = null

)