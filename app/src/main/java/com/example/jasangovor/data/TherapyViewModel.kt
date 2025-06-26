package com.example.jasangovor.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class TherapyViewModel: ViewModel() {
    private val db = Firebase.firestore

    private val _readingTexts = MutableStateFlow<List<ReadingText>>(emptyList())
    val readingTexts: StateFlow<List<ReadingText>> = _readingTexts

    private val _dailyExercises = MutableStateFlow<Map<String, DailyExercise>>(emptyMap())
    val dailyExercises: StateFlow<Map<String, DailyExercise>> = _dailyExercises

    init {
        fetchReadingTexts()
        fetchDailyExercises()
    }

    private fun fetchReadingTexts() {
        CoroutineScope(Dispatchers.IO).launch {
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

    private fun fetchDailyExercises() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = db.collection("dailyExercises").get().await()
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

    fun getExerciseById(exerciseId: Int?, dayIndex: Int?): Exercise? {
        if (exerciseId == null || dayIndex == null) return null

        val dayKey = "day_$dayIndex"
        return dailyExercises.value[dayKey]
            ?.exercises
            ?.values
            ?.firstOrNull { it.id == exerciseId }
    }

    fun markExerciseSolved(exerciseId: Int) {
        _dailyExercises.update { currentMap ->
            currentMap.mapValues { (docId, dailyExercise) ->
                val updatedExercises = dailyExercise.exercises.mapValues { (key, exercise) ->
                    if (exercise.id == exerciseId) exercise.copy(solved = true)
                    else exercise
                }
                val allSolved = updatedExercises.values.all { it.solved }
                dailyExercise.copy(
                    exercises = updatedExercises,
                    daySolved = allSolved
                )
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val documentEntry = _dailyExercises.value.entries.find { (_, daily) ->
                    daily.exercises.values.any { it.id == exerciseId }
                }
                val documentId = documentEntry?.key
                val dailyExercise = documentEntry?.value

                if (documentId != null && dailyExercise != null) {
                    val exerciseKey = dailyExercise.exercises.entries
                        .find { it.value.id == exerciseId }?.key

                    if (exerciseKey != null) {
                        db.collection("dailyExercises").document(documentId)
                            .update("exercises.$exerciseKey.solved", true)
                            .await()
                    }
                    if (dailyExercise.exercises.values.all { it.solved }) {
                        db.collection("dailyExercises").document(documentId)
                            .update("daySolved", true)
                            .await()
                    }
                }
            } catch (e: Exception) {
                Log.e("TherapyViewModel", "Error updating solved status", e)
            }
        }
    }

}