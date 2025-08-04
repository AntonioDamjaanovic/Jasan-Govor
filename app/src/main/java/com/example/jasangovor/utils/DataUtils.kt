package com.example.jasangovor.utils

import com.example.jasangovor.data.Note
import com.example.jasangovor.data.ReadingText
import com.example.jasangovor.data.StutteringAssessment
import java.time.Instant
import java.time.ZoneId

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
            val localDate = Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            localDate.month.value == month && localDate.year == year
        } ?: false
    }.sortedBy { it.date }
}

fun mapAssessmentsByDay(
    assessments: List<StutteringAssessment>
): Map<Int, StutteringAssessment> {
    return assessments.mapNotNull { assessment ->
        val day = assessment.date?.let { timestamp ->
            Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.systemDefault())
                .dayOfMonth
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