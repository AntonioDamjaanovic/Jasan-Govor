package com.example.jasangovor

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jasangovor.data.AuthState
import com.example.jasangovor.data.Note
import com.example.jasangovor.playback.AndroidAudioPlayer
import com.example.jasangovor.presentation.AuthViewModel
import com.example.jasangovor.presentation.JournalViewModel
import com.example.jasangovor.presentation.ProfileViewModel
import com.example.jasangovor.presentation.TherapyViewModel
import com.example.jasangovor.record.AndroidAudioRecorder
import com.example.jasangovor.ui.screens.journal.AddNoteScreen
import com.example.jasangovor.ui.screens.exercises.DailyPracticeScreen
import com.example.jasangovor.ui.screens.journal.EditNoteScreen
import com.example.jasangovor.ui.screens.exercises.ExerciseScreen
import com.example.jasangovor.ui.screens.HomeScreen
import com.example.jasangovor.ui.screens.journal.JournalScreen
import com.example.jasangovor.ui.screens.auth.LoginScreen
import com.example.jasangovor.ui.screens.journal.NoteScreen
import com.example.jasangovor.ui.screens.auth.ProfileScreen
import com.example.jasangovor.ui.screens.record.ReadingTextsScreen
import com.example.jasangovor.ui.screens.record.RecordScreen
import com.example.jasangovor.ui.screens.record.RecordingsScreen
import com.example.jasangovor.ui.screens.auth.RegisterScreen
import com.example.jasangovor.ui.screens.exercises.TrainingPlanScreen
import com.google.firebase.auth.FirebaseAuth
import java.io.File

object Routes {
    const val SCREEN_LOGIN = "login"
    const val SCREEN_REGISTER = "register"
    const val SCREEN_HOME = "home"
    const val SCREEN_PROFILE = "profile"
    const val SCREEN_TRAINING_PLAN = "trainingPlan"
    const val SCREEN_DAILY_PRACTICE = "dailyPractice/{dayIndex}"
    const val SCREEN_EXERCISE = "exercise/{exerciseId}?dayIndex={dayIndex}"
    const val SCREEN_RECORD_VOICE = "recordVoice/{selectedTextId}"
    const val SCREEN_RECORDINGS = "recordingsList"
    const val SCREEN_READING_TEXTS = "readingTexts"
    const val SCREEN_JOURNAL = "journal"
    const val SCREEN_NOTE = "note/{noteId}"
    const val SCREEN_ADD_NOTE = "addNote"
    const val SCREEN_EDIT_NOTE = "editNote/{noteId}"

    fun getDailyPracticePath(dayIndex: Int?): String {
        if (dayIndex != null && dayIndex != -1)
            return "dailyPractice/$dayIndex"
        return "dailyPractice/0"
    }

    fun getExercisePath(exerciseId: Int?, dayIndex: Int?): String {
        if (exerciseId != null && dayIndex != null)
            return "exercise/$exerciseId?dayIndex=$dayIndex"
        return "exercise/0?dayIndex=0"
    }
}

@Composable
fun NavigationController(
    authViewModel: AuthViewModel,
    profileViewModel: ProfileViewModel,
    therapyViewModel: TherapyViewModel,
    journalViewModel: JournalViewModel,
    recorder: AndroidAudioRecorder,
    player: AndroidAudioPlayer,
    cacheDir: File
) {
    val navController = rememberNavController()
    val authState by authViewModel.authState.collectAsStateWithLifecycle()

    LaunchedEffect(authState) {
        if (authState is AuthState.Unauthenticated) {
            navController.navigate(Routes.SCREEN_LOGIN) {
                popUpTo(0)
                launchSingleTop = true
            }
        }
    }

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
                authState = authState,
                onLogin = { email, password -> authViewModel.login(email, password) },
                onRegisterNavigate = { navController.navigate(Routes.SCREEN_REGISTER) },
                onErrorShown = { authViewModel.clearAuthState() },
                onAuthenticated = {
                    navController.navigate(Routes.SCREEN_HOME) {
                        popUpTo(Routes.SCREEN_LOGIN) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Routes.SCREEN_REGISTER) {
            RegisterScreen(
                authState = authState,
                onRegister = { email, password, passwordRepeated, name, surname ->
                    authViewModel.register(email, password, passwordRepeated, name, surname)
                },
                onErrorShown = { authViewModel.clearAuthState() },
                onAuthenticated = {
                    navController.navigate(Routes.SCREEN_HOME) {
                        popUpTo(Routes.SCREEN_REGISTER) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Routes.SCREEN_HOME) {
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if (uid != null) {
                profileViewModel.fetchUserProfile(uid)
            }
            val userProfile by profileViewModel.userProfile.collectAsStateWithLifecycle()

            HomeScreen(
                dayStreak = userProfile.dayStreak,
                onStartDailyExerciseClicked = { navController.navigate(Routes.SCREEN_TRAINING_PLAN) },
                onStartFastExerciseClicked = { navController.navigate(Routes.SCREEN_RECORD_VOICE) },
                onProfileClicked = { navController.navigate(Routes.SCREEN_PROFILE) },
                onJournalClicked = { navController.navigate(Routes.SCREEN_JOURNAL) }
            )
        }
        composable(Routes.SCREEN_PROFILE) {
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if (uid != null) {
                profileViewModel.fetchUserProfile(uid)
            }
            val userProfile by profileViewModel.userProfile.collectAsStateWithLifecycle()

            ProfileScreen(
                authState = authState,
                userEmail = userProfile.email,
                dayStreak = userProfile.dayStreak,
                onBackClicked = { navController.popBackStack() },
                signOut = { authViewModel.signOut() },
                onSignOutClicked = {
                    navController.navigate(Routes.SCREEN_LOGIN) {
                        popUpTo(0)
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(
            route = Routes.SCREEN_RECORD_VOICE,
            arguments = listOf(
                navArgument("selectedTextId") {
                    type = NavType.StringType
                    defaultValue = "null"
                    nullable = true
                }
            )
        ) { backStackEntry ->
            val textId = backStackEntry.arguments?.getString("selectedTextId")
            val readingTexts by therapyViewModel.readingTexts.collectAsStateWithLifecycle()

            RecordScreen(
                readingTexts = readingTexts,
                selectedTextId = if (textId == "null") null else textId,
                recorder = recorder,
                cacheDir = cacheDir,
                onBackClicked = { navController.popBackStack() },
                viewRecordings = { navController.navigate(Routes.SCREEN_RECORDINGS) },
                viewReadingTexts = { navController.navigate(Routes.SCREEN_READING_TEXTS) },
                fetchReadingTexts = { therapyViewModel.fetchReadingTexts() }
            )
        }
        composable(Routes.SCREEN_RECORDINGS) {
            val readingTexts by therapyViewModel.readingTexts.collectAsStateWithLifecycle()

            RecordingsScreen(
                cacheDir = cacheDir,
                player = player,
                readingTexts = readingTexts,
                onBackClicked = { navController.popBackStack() }
            )
        }
        composable(Routes.SCREEN_READING_TEXTS) {
            val readingTexts by therapyViewModel.readingTexts.collectAsStateWithLifecycle()

            ReadingTextsScreen(
                readingTexts = readingTexts,
                onBackClicked = { navController.popBackStack() },
                onTextClicked = { selectedTextId ->
                    navController.navigate(Routes.SCREEN_RECORD_VOICE.replace("{selectedTextId}", selectedTextId)) {
                        popUpTo(Routes.SCREEN_HOME) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Routes.SCREEN_TRAINING_PLAN) {
            val dailyExercises by therapyViewModel.dailyExercises.collectAsStateWithLifecycle()

            TrainingPlanScreen(
                dailyExercises = dailyExercises,
                onBackClicked = { navController.popBackStack() },
                onDayClicked = { dayIndex ->
                    navController.navigate(Routes.getDailyPracticePath(dayIndex))
                },
                fetchDailyExercises = { therapyViewModel.fetchDailyExercises() }
            )
        }
        composable(
            route = Routes.SCREEN_DAILY_PRACTICE,
            arguments = listOf(navArgument("dayIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            val dayIndex = backStackEntry.arguments?.getInt("dayIndex") ?: 1
            val selectedDayKey = "day_$dayIndex"
            val exercises = therapyViewModel.getExercisesFromDailyExercise(selectedDayKey)

            DailyPracticeScreen(
                exercises = exercises,
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
            val exercise = therapyViewModel.getExercise(exerciseId, dayIndex)

            ExerciseScreen(
                exercise = exercise,
                exerciseId = exerciseId,
                dayIndex = dayIndex,
                onExerciseSolved = { dayIndex, exerciseId ->
                    therapyViewModel.markExerciseSolved(dayIndex, exerciseId)
                },
                onBackClicked = { navController.popBackStack() }
            )
        }
        composable(Routes.SCREEN_JOURNAL) {
            val notes by journalViewModel.notes.collectAsStateWithLifecycle()

            JournalScreen(
                notes = notes,
                fetchNotes = { journalViewModel.fetchNotes() },
                onAddNote = { navController.navigate(Routes.SCREEN_ADD_NOTE) },
                onNoteClicked = { noteId ->
                    navController.navigate(Routes.SCREEN_NOTE.replace("{noteId}", noteId))
                },
                onBackClicked = { navController.popBackStack() }
            )
        }
        composable(
            route =  Routes.SCREEN_NOTE,
            arguments = listOf(navArgument("noteId") { type = NavType.StringType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId") ?: ""
            val note = journalViewModel.getNoteById(noteId) ?: Note()

            NoteScreen(
                note = note,
                onBackClicked = { navController.popBackStack() },
                onEditNoteClicked = { noteId ->
                    navController.navigate(Routes.SCREEN_EDIT_NOTE.replace("{noteId}", noteId))
                }
            )
        }
        composable(Routes.SCREEN_ADD_NOTE) {
            AddNoteScreen(
                addNote = { text ->
                    journalViewModel.addNote(text)
                },
                onBackClicked = { navController.popBackStack() }
            )
        }
        composable(
            route =  Routes.SCREEN_EDIT_NOTE,
            arguments = listOf(navArgument("noteId") { type = NavType.StringType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId") ?: ""
            val note = journalViewModel.getNoteById(noteId) ?: Note()

            EditNoteScreen(
                note = note,
                onBackClicked = { navController.popBackStack() },
                updateNote = { date, text ->
                    journalViewModel.updateNote(date, text)
                    navController.navigate(Routes.SCREEN_JOURNAL) {
                        popUpTo(Routes.SCREEN_HOME) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                deleteNote = { date ->
                    journalViewModel.deleteNote(date)
                    navController.navigate(Routes.SCREEN_JOURNAL) {
                        popUpTo(Routes.SCREEN_HOME) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}