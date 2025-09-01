package com.example.jasangovor.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jasangovor.data.displays.DayDisplay
import com.example.jasangovor.data.therapy.DailyExercise
import com.example.jasangovor.data.therapy.Exercise
import com.example.jasangovor.data.therapy.ReadingText
import com.example.jasangovor.data.therapy.TherapyManager
import com.example.jasangovor.utils.buildDayDisplays
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TherapyViewModel: ViewModel() {
    private val _readingTexts = MutableStateFlow<List<ReadingText>>(emptyList())
    val readingTexts: StateFlow<List<ReadingText>> = _readingTexts

    private val _dailyExercises = MutableStateFlow<Map<String, DailyExercise>>(emptyMap())
    val dailyExercises: StateFlow<Map<String, DailyExercise>> = _dailyExercises

    val dayDisplays: StateFlow<List<DayDisplay>> =
        dailyExercises.map { map -> buildDayDisplays(map) }
            .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val therapyManager = TherapyManager()

    fun getReadingTexts() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                _readingTexts.value = therapyManager.getReadingText()
            } catch (e: Exception) {
                Log.e("TherapyViewModel", "Firestore error", e)
            } finally {
                _loading.value = false
            }
        }
    }

    fun getReadingTextById(textId: String): ReadingText? {
        return readingTexts.value.find { it.id == textId }
    }

    fun getDailyExercises() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                _dailyExercises.value = therapyManager.getDailyExercises()
            } catch (e: Exception) {
                Log.e("TherapyViewModel", "Error fetching daily exercises", e)
            } finally {
                _loading.value = false
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
            _loading.value = true
            try {
                therapyManager.markExerciseSolved(dayKey, exerciseKey)
            } catch (e: Exception) {
                Log.e("TherapyViewModel", "Error updating solved status", e)
            } finally {
                _loading.value = false
            }
        }
    }
}