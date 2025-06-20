package com.example.jasangovor.utils

import java.io.File

fun getAllAudioFiles(cacheDir: File): List<File> {
    return cacheDir.listFiles { file ->
        file.extension == "mp3"
    }?.sortedByDescending { it.lastModified() } ?: emptyList()
}