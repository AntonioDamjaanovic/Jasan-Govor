package com.example.jasangovor.data.fearedsounds

import com.example.jasangovor.data.fearedsounds.FearedSoundExercises

data class FearedSound(
    val sound: String = "",
    val exercises: FearedSoundExercises = FearedSoundExercises()
)