package com.example.kms.utils

import com.example.kms.model.enums.AudienceType
import com.example.kms.model.enums.EmployeeType
import com.example.kms.model.enums.SignalisationType
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

object Converter {
    fun convertTimeToDate(time: Long): String {
        val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        utc.timeInMillis = time
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(utc.time)
    }

    fun convertDateFormat(date: String): String {
        var formatter: SimpleDateFormat =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val localDateTime = formatter.parse(date)
        val formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());
        return formatter2.format(localDateTime.toInstant())
    }

    fun convertSignalisation(signalisationType: SignalisationType): String =
        when (signalisationType) {
            SignalisationType.NONE -> "Отсутствует"
            SignalisationType.OFF -> "Выключена"
            SignalisationType.ON -> "Включена"
        }

    fun convertAudience(audienceType: AudienceType): String =
        when (audienceType) {
            AudienceType.ADMINISTRATION -> "Администрация"
            AudienceType.STUDY -> "Учебная"
            AudienceType.MULTIMEDIA -> "Мультимедиа"
            AudienceType.LAB -> "Лаборатория"
        }

    fun convertEmployeeType(employeeType: EmployeeType): String =
        when (employeeType) {
            EmployeeType.SECURITY -> "Охрана"
            EmployeeType.SERVICE -> "Сервис"
            EmployeeType.TEACHER -> "Преподаватель"
            EmployeeType.WATCHMAN -> "Вахтер"
        }


}