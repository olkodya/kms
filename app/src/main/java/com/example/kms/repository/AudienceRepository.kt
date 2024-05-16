package com.example.kms.repository

import com.example.kms.model.Audience

interface AudienceRepository {
    suspend fun getAudiences(): List<Audience>

}