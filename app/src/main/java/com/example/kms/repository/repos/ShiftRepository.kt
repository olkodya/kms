package com.example.kms.repository.repos

import com.example.kms.model.DTO.Shift

interface ShiftRepository {
    suspend fun createShift(userId: Int, watchId: Int): Shift

    suspend fun getShift(userId: Int): Shift

    suspend fun finishShift(id: Int): Shift

}