package com.example.kms.mapper

import com.example.kms.model.Shift
import com.example.kms.model.ShiftUIModel
import java.time.format.DateTimeFormatter

class ShiftUiModelMapper {
    private companion object {
        val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd")
    }

    fun map(shift: Shift): ShiftUIModel = with(shift) {
        ShiftUIModel(
            shift_id = shift_id,
//            start_date_time = FORMATTER.format(Instant.parse(start_date_time)),
            start_date_time = start_date_time,
            end_date_time = end_date_time,
            watch = watch,
            //   watchman = watchman,
        )
    }
}