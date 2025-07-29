package com.example.jasangovor.utils

import com.example.jasangovor.data.Note
import com.example.jasangovor.data.ReadingText
import com.example.jasangovor.data.StutteringAssessment
import java.util.Calendar
import java.util.Date

fun getReadingTextsCategories(readingTexts: List<ReadingText>): List<String> {
    return listOf("All") + readingTexts.map { it.id }
}

fun getReadingTextTitleByCategory(readingTexts: List<ReadingText>, category: String): String {
    return readingTexts.find { it.id == category }?.title ?: category
}

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

fun filterAssessmentsByMonth(
    assessments: List<StutteringAssessment>,
    month: Int,
    year: Int
): List<StutteringAssessment> {
    return assessments.filter { assessment ->
        assessment.date?.let { timestamp ->
            val calendar = Calendar.getInstance().apply {
                time = Date(timestamp)
            }
            calendar.get(Calendar.MONTH) == month && calendar.get(Calendar.YEAR) == year
        } ?: false
    }.sortedBy { it.date }
}

fun mapAssessmentsByDay(
    assessments: List<StutteringAssessment>
): Map<Int, StutteringAssessment> {
    return assessments.mapNotNull { assessment ->
        val day = assessment.date?.let { timestamp ->
            Calendar.getInstance().apply { time = Date(timestamp) }.get(Calendar.DAY_OF_MONTH)
        }
        if (day != null) day to assessment else null
    }.toMap()
}

fun getTextPreview(text: String): String {
    val firstLine = text.lineSequence().firstOrNull() ?: ""
    return if (firstLine.length > 30) {
        firstLine.take(40) + "..."
    } else if (firstLine.length < text.length) {
        "$firstLine..."
    } else {
        firstLine
    }
}