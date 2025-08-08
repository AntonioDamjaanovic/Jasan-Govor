package com.example.jasangovor.data.assessments

data class StutteringAssessment(
    val id: String = "",
    val level: StutteringLevel = StutteringLevel.NONE,
    val date: Long? = null
)