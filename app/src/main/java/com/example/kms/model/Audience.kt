package com.example.kms.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Audience (
    @SerialName("audience_id")
    val audience_id: Int?,
    @SerialName("number")
    val number: Int?,
//    @SerialName("floor")
//    val floor: Int?,
//    @SerialName("capacity")
//    val capacity: Int?,
//    @SerialName("signalisation")
//    val signalisation: String?,//TODO enum
    //val permissions: TODO
//    @SerialName("audienceType")
//    val audienceType: String?,//TODO enum
)