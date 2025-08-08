package com.example.jasangovor.data.exercises

import com.example.jasangovor.data.exercises.Exercise

data class DailyExercise(
    val daySolved: Boolean = false,
    val exercises: Map<String, Exercise> = emptyMap()
)