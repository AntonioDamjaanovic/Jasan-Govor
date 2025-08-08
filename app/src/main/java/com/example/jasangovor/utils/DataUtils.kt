package com.example.jasangovor.utils

import com.example.jasangovor.data.exercises.DailyExercise
import com.example.jasangovor.data.displays.DayDisplay
import com.example.jasangovor.data.exercises.Exercise
import com.example.jasangovor.data.displays.ExerciseDisplay
import com.example.jasangovor.data.notes.Note
import com.example.jasangovor.data.reading.ReadingText
import com.example.jasangovor.data.assessments.StutteringAssessment
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

fun buildDayDisplays(dailyExercises: Map<String, DailyExercise>): List<DayDisplay> {
    val sortedDayKeys = dailyExercises.keys.sortedBy { parseDayNumber(it) }
    val sortedDailyExercises = sortedDayKeys.map { dailyExercises[it] ?: DailyExercise() }
    val lockedFlags = computeLockedFlags(sortedDailyExercises)

    return sortedDayKeys.mapIndexed { index, dayKey ->
        val dayNumber = parseDayNumber(dayKey)
        DayDisplay(
            key = dayKey,
            dayNumber = dayNumber,
            dailyExercise = sortedDailyExercises[index],
            locked = lockedFlags[index]
        )
    }
}

fun buildExerciseDisplays(exercises: List<Exercise>): List<ExerciseDisplay> {
    return exercises.mapIndexed { index, exercise ->
        val locked = if (index == 0) {
            false
        } else {
            !exercises[index - 1].solved
        }
        ExerciseDisplay(exercise, locked)
    }
}

fun parseDayNumber(dayKey: String): Int =
    dayKey.substringAfter("day_").toIntOrNull() ?: 1

fun computeLockedFlags(dailyExercises: List<DailyExercise>): List<Boolean> {
    val locked = mutableListOf<Boolean>()
    for (i in dailyExercises.indices) {
        locked.add(if (i == 0) false else !dailyExercises[i - 1].daySolved)
    }
    return locked
}