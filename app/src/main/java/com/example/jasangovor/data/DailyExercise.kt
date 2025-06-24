package com.example.jasangovor.data

data class DailyExercise(
    val daySolved: Boolean = false,
    val exercises: Map<String, Exercise> = emptyMap()
)

data class Exercise(
    val solved: Boolean = false,
    val steps: List<String> = emptyList(),
    val title: String = "",
    val type: String = ""
)

