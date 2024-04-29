package com.example.kms.model

data class ShiftUIModel(
    val shift_id: Int,
    val start_date_time: String?,
    val end_date_time: String?,
    val watch: Watch,
    val watchman: LoginDto,
)