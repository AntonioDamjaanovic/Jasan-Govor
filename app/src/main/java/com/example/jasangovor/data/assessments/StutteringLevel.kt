package com.example.jasangovor.data.assessments

import com.example.jasangovor.R

enum class StutteringLevel(
    val displayName: String,
    val imageRes: Int
) {
    NONE("Bez mucanja", R.drawable.ic_stuttering_none),
    MILD("Blago mucanje", R.drawable.ic_stuttering_mild),
    MODERATE("Umjereno mucanje", R.drawable.ic_stuttering_moderate),
    FREQUENT("Često mucanje", R.drawable.ic_stuttering_frequent),
    SEVERE("Jako mucanje", R.drawable.ic_stuttering_severe)
}