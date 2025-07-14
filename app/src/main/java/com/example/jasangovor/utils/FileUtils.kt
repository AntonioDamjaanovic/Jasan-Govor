package com.example.jasangovor.utils

import com.example.jasangovor.record.AndroidAudioRecorder
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getAllAudioFiles(cacheDir: File): List<File> {
    return cacheDir.listFiles { file ->
        file.extension == "mp3"
    }?.sortedByDescending { it.lastModified() } ?: emptyList()
}

fun startAudioRecording(recorder: AndroidAudioRecorder, cacheDir: File) {
    val dateFormat = SimpleDateFormat("yyyy_MM_dd_HH_mm", Locale.getDefault())
    val currentDate = dateFormat.format(Date())
    val fileName = "audio_$currentDate.mp3"
    val file = File(cacheDir, fileName)
    recorder.start(file)
}