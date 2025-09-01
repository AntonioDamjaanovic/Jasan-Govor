package com.example.jasangovor.data.therapy

data class DailyExercise(
    val daySolved: Boolean = false,
    val exercises: Map<String, Exercise> = emptyMap()
)