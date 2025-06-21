package com.example.jasangovor.playback

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import java.io.File

class AndroidAudioPlayer(
    private val context: Context
): AudioPlayer {
    private var player: MediaPlayer? = null

    override fun playFile(file: File, onCompletion: () -> Unit) {
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

    override fun stop() {
        player?.stop()
        player?.release()
        player = null
    }
}