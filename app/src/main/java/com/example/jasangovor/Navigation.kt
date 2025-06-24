package com.example.jasangovor

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jasangovor.ui.DailyPracticeScreen
import com.example.jasangovor.ui.HomeScreen
import com.example.jasangovor.ui.LoginScreen
import com.example.jasangovor.ui.RecordScreen
import com.example.jasangovor.ui.RegisterScreen
import com.example.jasangovor.data.TherapyViewModel
import com.example.jasangovor.playback.AndroidAudioPlayer
import com.example.jasangovor.record.AndroidAudioRecorder
import com.example.jasangovor.ui.RecordingsScreen
import com.example.jasangovor.utils.getAllAudioFiles
import java.io.File

object Routes {
    const val SCREEN_HOME = "homeScreen"
    const val SCREEN_DAILY_PRACTICE = "dailyPractice"
    const val SCREEN_RECORD_VOICE = "recordVoice"
    const val SCREEN_RECORDINGS = "recordingsList"
    const val SCREEN_LOGIN = "loginScreen"
    const val SCREEN_REGISTER = "registerScreen"
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationController(
    therapyViewModel: TherapyViewModel,
    recorder: AndroidAudioRecorder,
    player: AndroidAudioPlayer,
    audioFileState: MutableState<File?>,
    cacheDir: File
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SCREEN_HOME,
        enterTransition = { fadeIn(animationSpec = tween(100))},
        exitTransition = { fadeOut(animationSpec = tween(100)) },
        popEnterTransition = { fadeIn(animationSpec = tween(100)) },
        popExitTransition = { fadeOut(animationSpec = tween(100)) }
    ) {
        composable(Routes.SCREEN_LOGIN) {
            LoginScreen(
                navigation = navController,
                therapyViewModel = therapyViewModel
            )
        }
        composable(Routes.SCREEN_REGISTER) {
            RegisterScreen(
                navigation = navController,
                therapyViewModel = therapyViewModel
            )
        }
        composable(Routes.SCREEN_HOME) {
            HomeScreen(
                navigation = navController,
            )
        }
        composable(Routes.SCREEN_RECORD_VOICE) {
            RecordScreen(
                navigation = navController,
                therapyViewModel = therapyViewModel,
                recorder = recorder,
                audioFileState = audioFileState,
                cacheDir = cacheDir
            )
        }
        composable(Routes.SCREEN_RECORDINGS) {
            RecordingsScreen(
                navigation = navController,
                cacheDir = cacheDir,
                player = player,
            )
        }
        composable(Routes.SCREEN_DAILY_PRACTICE) {
            DailyPracticeScreen(
                navigation = navController,
                therapyViewModel = therapyViewModel
            )
        }
    }
}