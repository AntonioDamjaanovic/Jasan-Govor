package com.example.jasangovor.data

data class DailyExercise(
    val daySolved: Boolean = false,
    val exercises: Map<String, Exercise> = emptyMap()
)
