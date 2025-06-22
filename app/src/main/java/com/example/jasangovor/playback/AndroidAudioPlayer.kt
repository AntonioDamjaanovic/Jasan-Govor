package com.example.jasangovor.playback

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import java.io.File

class AndroidAudioPlayer(
    private val context: Context
): AudioPlayer {
    private var player: MediaPlayer? = null
    private var isPaused: Boolean = false
    private var currentFile: File? = null

    override fun playFile(file: File, onCompletion: () -> Unit) {
        if (player != null && isPaused && currentFile == file) {
            player?.start()
            isPaused = false
        } else {
            stop()
            player = MediaPlayer().apply {
                setDataSource(context, file.toUri())
                prepare()
                start()
                setOnCompletionListener {
                    onCompletion()
                    stop()
                }
            }
        }
        currentFile = file
        isPaused = false
    }

    override fun pause() {
        if (player?.isPlaying == true) {
            player?.pause()
            isPaused = true
        }
    }

    override fun stop() {
        player?.stop()
        player?.release()
        player = null
    }

    fun isPlaying(file: File): Boolean {
        return player?.isPlaying == true && currentFile == file && !isPaused
    }
}