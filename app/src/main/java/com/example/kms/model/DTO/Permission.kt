package com.example.kms.model.DTO

import kotlinx.serialization.Serializable

@Serializable
data class Permission(
    val permission_id: Int?,
    val name: String?,
//    val employees:List<Employee?>?,
//    val divisions: List<Int?>?,
//    val audiences: List<Int?>?
)