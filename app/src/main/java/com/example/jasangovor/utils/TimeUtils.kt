package com.example.jasangovor.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun formatDate(date: String): String {
    val dateText = try {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val newDate = sdf.parse(date)
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(newDate!!)
    } catch (e: Exception) {
        date
    }
    return dateText
}

fun formatMonthName(
    month: Int,
    year: Int,
    locale: Locale = Locale.getDefault()
): String {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month)
        set(Calendar.DAY_OF_MONTH, 1)
    }
    val formatted = SimpleDateFormat("LLLL yyyy", locale).format(calendar.time)
    return formatted.replaceFirstChar { it.uppercaseChar() }
}

fun getDaysInMonth(month: Int, year: Int): Int {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month)
        set(Calendar.DAY_OF_MONTH, 1)
    }
    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
}

fun floorToDayMillis(timeMillis: Long): Long {
    val cal = Calendar.getInstance().apply { timeInMillis = timeMillis }
    cal.set(Calendar.HOUR_OF_DAY, 0); cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0); cal.set(Calendar.MILLISECOND, 0)
    return cal.timeInMillis
}
fun ceilToDayMillis(timeMillis: Long): Long {
    val cal = Calendar.getInstance().apply { timeInMillis = timeMillis }
    cal.set(Calendar.HOUR_OF_DAY, 23); cal.set(Calendar.MINUTE, 59)
    cal.set(Calendar.SECOND, 59); cal.set(Calendar.MILLISECOND, 999)
    return cal.timeInMillis
}
