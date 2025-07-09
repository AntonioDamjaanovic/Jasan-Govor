package com.example.jasangovor

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jasangovor.ui.DailyPracticeScreen
import com.example.jasangovor.ui.HomeScreen
import com.example.jasangovor.ui.LoginScreen
import com.example.jasangovor.ui.RecordScreen
import com.example.jasangovor.ui.RegisterScreen
import com.example.jasangovor.presentation.TherapyViewModel
import com.example.jasangovor.playback.AndroidAudioPlayer
import com.example.jasangovor.presentation.AuthViewModel
import com.example.jasangovor.record.AndroidAudioRecorder
import com.example.jasangovor.ui.ExerciseScreen
import com.example.jasangovor.ui.RecordingsScreen
import com.example.jasangovor.ui.TrainingPlanScreen
import java.io.File

object Routes {
    const val SCREEN_LOGIN = "login"
    const val SCREEN_REGISTER = "register"
    const val SCREEN_HOME = "home"
    const val SCREEN_TRAINING_PLAN = "trainingPlan"
    const val SCREEN_DAILY_PRACTICE = "dailyPractice/{dayIndex}"
    const val SCREEN_EXERCISE = "exercise/{exerciseId}?dayIndex={dayIndex}"
    const val SCREEN_RECORD_VOICE = "recordVoice"
    const val SCREEN_RECORDINGS = "recordingsList"

    fun getDailyPracticePath(dayIndex: Int?): String {
        if (dayIndex != null && dayIndex != -1)
            return "dailyPractice/$dayIndex"
        return "dailyPractice/0"
    }

    fun getExercisePath(exerciseId: Int?, dayIndex: Int?): String {
        return "exercise/$exerciseId?dayIndex=$dayIndex"
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationController(
    authViewModel: AuthViewModel,
    therapyViewModel: TherapyViewModel,
    recorder: AndroidAudioRecorder,
    player: AndroidAudioPlayer,
    cacheDir: File
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.SCREEN_LOGIN,
        enterTransition = { fadeIn(animationSpec = tween(100))},
        exitTransition = { fadeOut(animationSpec = tween(100)) },
        popEnterTransition = { fadeIn(animationSpec = tween(100)) },
        popExitTransition = { fadeOut(animationSpec = tween(100)) }
    ) {
        composable(Routes.SCREEN_LOGIN) {
            LoginScreen(
                authViewModel = authViewModel,
                onLoginClicked = { navController.navigate(Routes.SCREEN_HOME) },
                onRegisterClicked = { navController.navigate(Routes.SCREEN_REGISTER) },
            )
        }
        composable(Routes.SCREEN_REGISTER) {
            RegisterScreen(
                authViewModel = authViewModel,
                onRegisterClicked = { navController.navigate(Routes.SCREEN_HOME) },
            )
        }
        composable(Routes.SCREEN_HOME) {
            HomeScreen(
                onStartDailyExerciseClicked = { navController.navigate(Routes.SCREEN_TRAINING_PLAN) },
                onStartFastExerciseClicked = { navController.navigate(Routes.SCREEN_RECORD_VOICE) }
            )
        }
        composable(Routes.SCREEN_RECORD_VOICE) {
            RecordScreen(
                therapyViewModel = therapyViewModel,
                recorder = recorder,
                cacheDir = cacheDir,
                onBackClicked = { navController.popBackStack() },
                onViewRecordingsClicked = { navController.navigate(Routes.SCREEN_RECORDINGS) }
            )
        }
        composable(Routes.SCREEN_RECORDINGS) {
            RecordingsScreen(
                cacheDir = cacheDir,
                player = player,
                onBackClicked = { navController.popBackStack() }
            )
        }
        composable(Routes.SCREEN_TRAINING_PLAN) {
            TrainingPlanScreen(
                therapyViewModel = therapyViewModel,
                onBackClicked = { navController.popBackStack() },
                onDayClicked = { dayIndex ->
                    navController.navigate(Routes.getDailyPracticePath(dayIndex))
                },
                onInfoClicked = {  }
            )
        }
        composable(
            route = Routes.SCREEN_DAILY_PRACTICE,
            arguments = listOf(navArgument("dayIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            val dayIndex = backStackEntry.arguments?.getInt("dayIndex") ?: 1
            DailyPracticeScreen(
                therapyViewModel = therapyViewModel,
                dayIndex = dayIndex,
                onBackClicked = { navController.popBackStack() },
                onExerciseClicked = { exerciseID, dayIndex ->
                    navController.navigate(Routes.getExercisePath(exerciseID, dayIndex))
                }
            )
        }
        composable(
            route = Routes.SCREEN_EXERCISE,
            arguments = listOf(
                navArgument("exerciseId") { type = NavType.IntType },
                navArgument("dayIndex") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val exerciseId = backStackEntry.arguments?.getInt("exerciseId") ?: 1
            val dayIndex = backStackEntry.arguments?.getInt("dayIndex") ?: 1
            ExerciseScreen(
                therapyViewModel = therapyViewModel,
                exerciseId = exerciseId,
                dayIndex = dayIndex,
                onBackClicked = { navController.popBackStack() }
            )
        }
    }
}