package com.example.jasangovor.playback

import java.io.File

interface AudioPlayer {
    fun playFile(file: File, onCompletion: () -> Unit = {})
    fun stop()
    fun pause()
}