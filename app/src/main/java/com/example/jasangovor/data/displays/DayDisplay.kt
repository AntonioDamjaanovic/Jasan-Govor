package com.example.jasangovor.data.displays

import com.example.jasangovor.data.exercises.DailyExercise

data class DayDisplay(
    val key: String,
    val dayNumber: Int,
    val dailyExercise: DailyExercise,
    val locked: Boolean
)