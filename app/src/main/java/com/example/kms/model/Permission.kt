package com.example.kms.model

import kotlinx.serialization.Serializable

@Serializable
data class Permission(
    val permission_id: Int?,
    val name: String?,
//    val employees:List<Employee>?,
//    val divissions: List<Division?>?,
//    val audiences: List<Int?>?
)