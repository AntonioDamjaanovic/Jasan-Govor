package com.example.jasangovor.utils

import com.example.jasangovor.record.AndroidAudioRecorder
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun startAudioRecording(recorder: AndroidAudioRecorder, cacheDir: File, textId: String) {
    val dateFormat = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
    val currentDate = dateFormat.format(Date())
    val fileName = "${textId}_${currentDate}.mp3"
    val file = File(cacheDir, fileName)
    recorder.start(file)
}

fun getAllAudioFiles(cacheDir: File): List<File> {
    return cacheDir.listFiles { file ->
        file.extension == "mp3"
    }?.sortedByDescending { it.lastModified() } ?: emptyList()
}

fun filterAudioFilesByCategory(audioFiles: List<File>, category: String): List<File> {
    return audioFiles.filter { it.name.startsWith("${category}_") }
}