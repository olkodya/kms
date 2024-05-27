package com.example.kms.repository.reposImpl

import com.example.kms.model.DTO.Shift
import com.example.kms.network.api.ShiftApi
import com.example.kms.repository.repos.ShiftRepository

class ShiftRepositoryImpl(
    private val api: ShiftApi
) : ShiftRepository {
    override suspend fun createShift(userId: Int, watchId: Int): Shift {
        return api.createShift(userId, watchId)
    }

    override suspend fun getShift(userId: Int): Shift {
        return api.getShift(userId)
    }

    override suspend fun finishShift(id: Int): Shift {
        return api.finishShift(id)
    }
}