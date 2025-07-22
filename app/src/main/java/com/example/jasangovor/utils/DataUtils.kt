package com.example.jasangovor.utils

import com.example.jasangovor.data.ReadingText

fun getReadingTextsCategories(readingTexts: List<ReadingText>): List<String> {
    return listOf("All") + readingTexts.map { it.id }
}

fun getReadingTextTitleByCategory(readingTexts: List<ReadingText>, category: String): String {
    return readingTexts.find { it.id == category }?.title ?: category
}