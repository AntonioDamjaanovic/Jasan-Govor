package com.example.jasangovor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import com.example.jasangovor.data.TherapyViewModel
import com.example.jasangovor.playback.AndroidAudioPlayer
import com.example.jasangovor.record.AndroidAudioRecorder
import com.example.jasangovor.ui.theme.JasanGovorTheme
import java.io.File

class MainActivity : ComponentActivity() {
    private val recorder by lazy {
        AndroidAudioRecorder(applicationContext)
    }
    private val player by lazy {
        AndroidAudioPlayer(applicationContext)
    }

    override fun onPause() {
        super.onPause()
        recorder.stop()
        player.stop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.RECORD_AUDIO),
            0
        )

        val therapyViewModel by viewModels<TherapyViewModel>()

        enableEdgeToEdge()
        setContent {
            JasanGovorTheme {
                NavigationController(
                    therapyViewModel = therapyViewModel,
                    recorder = recorder,
                    player = player,
                    cacheDir = cacheDir
                )
            }
        }
    }
}
