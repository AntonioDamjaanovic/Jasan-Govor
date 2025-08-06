package com.example.jasangovor.data

data class FearedSoundExercises(
    val flexibleRate: FearedSoundExerciseDetail = FearedSoundExerciseDetail(),
    val pullOuts: FearedSoundExerciseDetail = FearedSoundExerciseDetail(),
    val preparatorySets: FearedSoundExerciseDetail = FearedSoundExerciseDetail()
)