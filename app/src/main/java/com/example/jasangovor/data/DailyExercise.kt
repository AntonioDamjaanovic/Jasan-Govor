package com.example.jasangovor.data

data class DailyExercise(
    val daySolved: Boolean = false,
    val exercises: Map<String, Exercise> = emptyMap()
)

data class Exercise(
    val id: Int = 0,
    var solved: Boolean = false,
    val steps: List<String> = emptyList(),
    val title: String = "",
    val type: String = ""
)
