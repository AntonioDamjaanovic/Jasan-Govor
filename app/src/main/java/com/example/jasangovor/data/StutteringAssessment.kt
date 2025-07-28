package com.example.jasangovor.data

data class StutteringAssessment(
    val id: String = "",
    val level: StutteringLevel = StutteringLevel.NONE,
    val date: Long? = null
)
