package com.example.jasangovor.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jasangovor.data.assessments.AssessmentManager
import com.example.jasangovor.data.assessments.StutteringAssessment
import com.example.jasangovor.data.assessments.StutteringLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AssessmentViewModel(): ViewModel() {
    private val _assessments = MutableStateFlow<List<StutteringAssessment>>(emptyList())
    val assessments: StateFlow<List<StutteringAssessment>> = _assessments
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val assessmentManager = AssessmentManager()

    fun getAssessments() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                _assessments.value = assessmentManager.getAssessments()
            } catch (e: Exception) {
                Log.e("AssessmentViewModel", "Error with fetching assessments", e)
            } finally {
                _loading.value = false
            }
        }
    }

    fun addAssessment(level: StutteringLevel) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                assessmentManager.addAssessment(level)
            } catch (e: Exception) {
                Log.e("AssessmentViewModel", "Error with adding assessment", e)
            } finally {
                _loading.value = false
            }
        }
    }
}