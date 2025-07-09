package com.example.jasangovor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.jasangovor.presentation.TherapyViewModel
import com.example.jasangovor.playback.AndroidAudioPlayer
import com.example.jasangovor.presentation.AuthViewModel
import com.example.jasangovor.record.AndroidAudioRecorder
import com.example.jasangovor.ui.theme.JasanGovorTheme

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
        val therapyViewModel by viewModels<TherapyViewModel>()
        val authViewModel by viewModels<AuthViewModel>()

        enableEdgeToEdge()
        setContent {
            JasanGovorTheme {
                NavigationController(
                    authViewModel = authViewModel,
                    therapyViewModel = therapyViewModel,
                    recorder = recorder,
                    player = player,
                    cacheDir = cacheDir
                )
            }
        }
    }
}
