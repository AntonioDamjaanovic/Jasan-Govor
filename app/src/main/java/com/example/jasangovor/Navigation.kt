package com.example.jasangovor

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.jasangovor.ui.DailyPracticeScreen
import com.example.jasangovor.ui.HomeScreen
import com.example.jasangovor.ui.LoginScreen
import com.example.jasangovor.ui.RecordScreen
import com.example.jasangovor.ui.RegisterScreen


object Routes {
    const val SCREEN_HOME = "homeScreen"
    const val SCREEN_DAILY_PRACTICE = "dailyPractice"   // TODO add {practiceId} to route
    const val SCREEN_RECORD_VOICE = "recordVoice"
    const val SCREEN_LOGIN = "loginScreen"
    const val SCREEN_REGISTER = "registerScreen"

    fun getDailyPracticePath(practiceId: Int?): String {
        if (practiceId != null && practiceId != -1)
            return "dailyPractice/$practiceId"
        return "dailyPractice/0"
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationController(
    // appViewModel = appViewModel
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
                navigation = navController
            )
        }
        composable(Routes.SCREEN_REGISTER) {
            RegisterScreen(
                navigation = navController
            )
        }
        composable(Routes.SCREEN_HOME) {
            HomeScreen(
                // appViewModel = appViewModel
                navigation = navController
            )
        }
        composable(Routes.SCREEN_RECORD_VOICE) {
            RecordScreen(
                // appViewModel = appViewModel
                navigation = navController
            )
        }
        composable(
            Routes.SCREEN_DAILY_PRACTICE,
            /*
            arguments = listOf(
                navArgument("practiceId") {
                    type = NavType.IntType
                }
            )
            */
        ) {
            backStackEntry ->
                backStackEntry.arguments?.getInt("practiceId")?.let {
                    DailyPracticeScreen(
                        // appViewModel = appViewModel
                        navigation = navController,
                        //practiceId = it
                    )
                }
        }
    }
}