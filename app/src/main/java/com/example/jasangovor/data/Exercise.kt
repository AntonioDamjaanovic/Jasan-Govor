package com.example.jasangovor.data

data class Exercise(
    val id: Int = 0,
    val solved: Boolean = false,
    val steps: List<String> = emptyList(),
    val title: String = "",
    val type: String = ""
)