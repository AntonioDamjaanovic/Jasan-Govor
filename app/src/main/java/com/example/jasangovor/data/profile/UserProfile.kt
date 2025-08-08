package com.example.jasangovor.data.profile

data class UserProfile(
    val email: String = "",
    val username: String = "",
    val dayStreak: Int = 0,
    val lastOpenedDate: String = ""
)