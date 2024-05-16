package com.example.kms.repository

import com.example.kms.model.Audience
import com.example.kms.network.api.AudienceApi

class AudienceRepositoryImpl(private val api: AudienceApi) : AudienceRepository {
    override suspend fun getAudiences(): List<Audience> = api.getAudiences()
}