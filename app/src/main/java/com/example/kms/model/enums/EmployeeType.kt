package com.example.kms.model.enums

import kotlinx.serialization.Serializable

@Serializable
enum class EmployeeType {
    SERVICE,
    TEACHER,
    SECURITY,
    WATCHMAN
}