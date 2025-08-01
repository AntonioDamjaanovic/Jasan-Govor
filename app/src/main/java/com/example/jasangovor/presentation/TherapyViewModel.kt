package com.example.jasangovor.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jasangovor.data.DailyExercise
import com.example.jasangovor.data.DayDisplay
import com.example.jasangovor.data.Exercise
import com.example.jasangovor.data.ReadingText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class TherapyViewModel: ViewModel() {
    private val db = Firebase.firestore

    private val _readingTexts = MutableStateFlow<List<ReadingText>>(emptyList())
    val readingTexts: StateFlow<List<ReadingText>> = _readingTexts

    private val _dailyExercises = MutableStateFlow<Map<String, DailyExercise>>(emptyMap())
    val dailyExercises: StateFlow<Map<String, DailyExercise>> = _dailyExercises

    val dayDisplays: StateFlow<List<DayDisplay>> =
        dailyExercises.map { map ->
            val sortedDayKeys = map.keys.sortedBy { it.substringAfter("day_").toIntOrNull() ?: 0 }
            val dayDisplays = mutableListOf<DayDisplay>()
            for ((i, dayKey) in sortedDayKeys.withIndex()) {
                val dayNumber = dayKey.substringAfter("day_").toIntOrNull() ?: 1
                val dailyExercise = map[dayKey] ?: DailyExercise()
                val locked = if (i == 0) false else !dayDisplays[i - 1].dailyExercise.daySolved
                dayDisplays.add(DayDisplay(dayKey, dayNumber, dailyExercise, locked))
            }
            dayDisplays
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun fetchReadingTexts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = db.collection("readingTexts").get().await()
                val texts = result.documents.mapNotNull {
                    it.toObject(ReadingText::class.java)
                }
                _readingTexts.value = texts
            } catch (e: Exception) {
                Log.e("TherapyViewModel", "Firestore error", e)
            }
        }
    }

    fun getReadingText(textId: String): ReadingText? {
        return readingTexts.value.firstOrNull { it.id == textId }
    }

    fun fetchDailyExercises() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = db.collection("users")
                    .document(userId)
                    .collection("dailyExercises")
                    .get()
                    .await()
                val exerciseMap = result.documents.associate { doc ->
                    val dailyExercise = doc.toObject(DailyExercise::class.java)
                    doc.id to (dailyExercise ?: DailyExercise())
                }
                _dailyExercises.value = exerciseMap
            } catch (e: Exception) {
                Log.e("TherapyViewModel", "Error fetching daily exercises", e)
            }
        }
    }

    fun getExercisesFromDailyExercise(dayKey: String): List<Exercise>? {
        val dailyExercise = dailyExercises.value[dayKey]
        return dailyExercise?.exercises
            ?.entries
            ?.sortedBy { it.key.substringAfter("_").toIntOrNull() ?: Int.MAX_VALUE }
            ?.map { it.value }
    }

    fun getExercise(exerciseId: Int, dayIndex: Int): Exercise? {
        val dayKey = "day_$dayIndex"
        return dailyExercises.value[dayKey]
            ?.exercises
            ?.values
            ?.firstOrNull { it.id == exerciseId }
    }

    fun markExerciseSolved(dayIndex: Int, exerciseId: Int) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val dayKey = "day_$dayIndex"
        val dailyExercise = _dailyExercises.value[dayKey] ?: return

        val exerciseKey = dailyExercise.exercises.entries
            .find { it.value.id == exerciseId }
            ?.key ?: return

        _dailyExercises.update { exercisesMap ->
            exercisesMap.mapValues { (docId, daily) ->
                if (docId == dayKey) {
                    val updatedExercises = daily.exercises.mapValues { (key, exercise) ->
                        if (key == exerciseKey) exercise.copy(solved = true)
                        else exercise
                    }
                    val allSolved = updatedExercises.values.all { it.solved }
                    daily.copy(
                        exercises = updatedExercises,
                        daySolved = allSolved
                    )
                } else daily
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                db.collection("users")
                    .document(userId)
                    .collection("dailyExercises")
                    .document(dayKey)
                    .update("exercises.$exerciseKey.solved", true)
                    .await()

                val updatedDaily = _dailyExercises.value[dayKey]
                if (updatedDaily != null && updatedDaily.exercises.values.all { it.solved }) {
                    db.collection("users")
                        .document(userId)
                        .collection("dailyExercises")
                        .document(dayKey)
                        .update("daySolved", true)
                        .await()
                }
            } catch (e: Exception) {
                Log.e("TherapyViewModel", "Error updating solved status", e)
            }
        }
    }
}