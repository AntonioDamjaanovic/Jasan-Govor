package com.example.jasangovor.utils

import com.example.jasangovor.data.Note
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun filterNotesByDate(
    notes: List<Note>,
    startDate: Long?,
    endDate: Long?
): List<Note> {
    return notes.filter { note ->
        val noteTime = note.date ?: 0L
        val afterStart = startDate?.let { noteTime >= floorToDayMillis(it) } ?: true
        val beforeEnd = endDate?.let { noteTime <= ceilToDayMillis(it) } ?: true
        afterStart && beforeEnd
    }
}

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
