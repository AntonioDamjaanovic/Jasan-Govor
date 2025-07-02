package com.example.jasangovor.playback

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.core.net.toUri
import java.io.File

class AndroidAudioPlayer(
    private val context: Context
): AudioPlayer {
    private val player by lazy { MediaPlayer() }
    private var isPaused: Boolean = false
    private var currentFile: File? = null

    override fun playFile(file: File, onCompletion: () -> Unit) {
        if (isPaused && currentFile == file) {
            player.start()
            isPaused = false
        } else {
            stop()
            currentFile = file
            try {
                player.reset()
                player.setDataSource(context, file.toUri())
                player.prepare()
                player.start()
                player.setOnCompletionListener {
                    onCompletion()
                    stop()
                }
            } catch (e: Exception) {
                Log.e("AndroidAudioPlayer", "Error with playing file", e)
            }
        }
        isPaused = false
    }

    override fun pause() {
        if (player.isPlaying) {
            player.pause()
            isPaused = true
        }
    }

    override fun stop() {
        if (player.isPlaying || isPaused) {
            player.stop()
            try {
                player.reset()
            } catch (e: Exception) {
                Log.e("AndroidAudioPlayer", "Error with stoping file", e)
            }
        }
        isPaused = false
        currentFile = null
    }

    fun isPlaying(file: File): Boolean {
        return player.isPlaying && currentFile == file && !isPaused
    }
}