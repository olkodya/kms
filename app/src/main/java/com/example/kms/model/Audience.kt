package com.example.kms.model


import com.example.kms.model.enums.AudienceType
import com.example.kms.model.enums.SignalisationType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Audience (
    @SerialName("audience_id")
    val audience_id: Int?,
    @SerialName("exist")
    val exists: Boolean?,
    @SerialName("number")
    val number: Int?,
    @SerialName("capacity")
    val capacity: Int?,
    @SerialName("signalisation")
    val signalisation: SignalisationType?,
//    @SerialName("permissions")
//    val permissions: List<Permission?>?,
    @SerialName("audience_type")
    val audienceType: AudienceType?,
)