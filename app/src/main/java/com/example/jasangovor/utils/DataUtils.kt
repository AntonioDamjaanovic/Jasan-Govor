package com.example.jasangovor.utils

import com.example.jasangovor.data.ReadingText
import java.text.SimpleDateFormat
import java.util.Locale

fun getReadingTextsCategories(readingTexts: List<ReadingText>): List<String> {
    return listOf("All") + readingTexts.map { it.id }
}

fun getReadingTextTitleByCategory(readingTexts: List<ReadingText>, category: String): String {
    return readingTexts.find { it.id == category }?.title ?: category
}

fun formatDate(date: String): String {
    val dateText = try {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val newDate = sdf.parse(date)
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(newDate!!)
    } catch (e: Exception) {
        date
    }
    return dateText
}