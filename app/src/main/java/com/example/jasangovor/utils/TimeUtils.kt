package com.example.jasangovor.utils

import java.time.Instant
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatDate(date: String): String {
    return try {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
        val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault())
        val localDate = LocalDate.parse(date, inputFormatter)
        localDate.format(outputFormatter)
    } catch (e: Exception) {
        date
    }
}

fun formatMonthName(
    month: Int,
    year: Int,
    locale: Locale = Locale.getDefault()
): String {
    val localDate = LocalDate.of(year, month, 1)
    val formatter = DateTimeFormatter.ofPattern("LLLL yyyy", locale)
    val formatted = localDate.format(formatter)
    return formatted.replaceFirstChar { it.uppercaseChar() }
}

fun getDaysInMonth(month: Int, year: Int): Int {
    return YearMonth.of(year, month).lengthOfMonth()
}

fun formatMillisToDateString(millis: Long?): String {
    val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault())
    return if (millis != null) {
        val localDate = Instant.ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        localDate.format(dateFormatter)
    } else {
        ""
    }
}

fun floorToDayMillis(millis: Long): Long {
    val zone = ZoneId.systemDefault()
    val localDate = Instant.ofEpochMilli(millis).atZone(zone).toLocalDate()
    return localDate.atStartOfDay(zone).toInstant().toEpochMilli()
}
fun ceilToDayMillis(millis: Long): Long {
    val zone = ZoneId.systemDefault()
    val localDate = Instant.ofEpochMilli(millis).atZone(zone).toLocalDate()
    val endOfDay = localDate.atTime(23, 59, 59).atZone(zone)
    return endOfDay.toInstant().toEpochMilli()
}
