package com.example.kms.repository

import com.example.kms.model.Audience
import com.example.kms.model.enums.AudienceForm

interface AudienceRepository {
    suspend fun getAudiences(): List<Audience>

    suspend fun getByID(id: Int): Audience

    suspend fun update(id: Int, audienceForm: AudienceForm): Audience

}