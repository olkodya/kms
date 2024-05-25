package com.example.kms.repository

import com.example.kms.model.Audience
import com.example.kms.model.enums.AudienceForm
import com.example.kms.network.api.AudienceApi

class AudienceRepositoryImpl(private val api: AudienceApi) : AudienceRepository {
    override suspend fun getAudiences(): List<Audience> = api.getAudiences()
    override suspend fun getByID(id: Int): Audience = api.getAudienceById(id)
    override suspend fun update(id: Int, audienceForm: AudienceForm): Audience =
        api.updateAudience(id, audienceForm)
}