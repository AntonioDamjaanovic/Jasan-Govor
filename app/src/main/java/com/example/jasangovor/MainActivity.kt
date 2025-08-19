package com.example.jasangovor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.jasangovor.playback.AndroidAudioPlayer
import com.example.jasangovor.presentation.AssessmentViewModel
import com.example.jasangovor.presentation.AuthViewModel
import com.example.jasangovor.presentation.FearedSoundsViewModel
import com.example.jasangovor.presentation.JournalViewModel
import com.example.jasangovor.presentation.ProfileViewModel
import com.example.jasangovor.presentation.TherapyViewModel
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
        val authViewModel by viewModels<AuthViewModel>()
        val profileViewModel by viewModels<ProfileViewModel>()
        val therapyViewModel by viewModels<TherapyViewModel>()
        val journalViewModel by viewModels<JournalViewModel>()
        val assessmentViewModel by viewModels<AssessmentViewModel>()
        val fearedSoundsViewModel by viewModels<FearedSoundsViewModel>()

        enableEdgeToEdge()
        setContent {
            JasanGovorTheme {
                NavigationController(
                    authViewModel = authViewModel,
                    profileViewModel = profileViewModel,
                    therapyViewModel = therapyViewModel,
                    journalViewModel = journalViewModel,
                    fearedSoundsViewModel = fearedSoundsViewModel,
                    assessmentViewModel = assessmentViewModel,
                    recorder = recorder,
                    player = player,
                    cacheDir = cacheDir
                )
            }
        }
    }
}
