package com.example.jasangovor.data

data class DayDisplay(
    val key: String,
    val dayNumber: Int,
    val dailyExercise: DailyExercise,
    val locked: Boolean
)
