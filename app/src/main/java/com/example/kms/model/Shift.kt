package com.example.kms.model

import com.example.kms.utils.InstantSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class Shift (
    @SerialName("shift_id")
    val shift_id: Int,
    @SerialName("start_date_time")
    val start_date_time: String?,
    @SerialName("end_date_time")
    val end_date_time: String?,
    @SerialName("watch")
    val watch: Watch,
    @SerialName("watchman")
    val watchman: LoginDto,
)