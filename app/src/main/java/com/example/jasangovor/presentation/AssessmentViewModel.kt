package com.example.jasangovor.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jasangovor.data.StutteringAssessment
import com.example.jasangovor.data.StutteringLevel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class AssessmentViewModel: ViewModel() {
    private val db = Firebase.firestore

    private val _assessments = MutableStateFlow<List<StutteringAssessment>>(emptyList())
    val assessments: StateFlow<List<StutteringAssessment>> = _assessments
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun fetchAssessments() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = db.collection("users")
                    .document(userId)
                    .collection("assessments")
                    .orderBy("date", Query.Direction.DESCENDING)
                    .get()
                    .await()
                val assessments = result.documents.mapNotNull { doc ->
                    val level = StutteringLevel.valueOf(doc.getString("level") ?: "NONE")
                    val id = doc.id
                    val date = doc.getTimestamp("date")?.toDate()?.time
                    StutteringAssessment(
                        id = id,
                        level = level,
                        date = date
                    )
                }
                _assessments.value = assessments
            } catch (e: Exception) {
                Log.e("AssessmentViewModel", "Error with fetching assessments", e)
            }
        }
    }

    fun addAssessment(level: StutteringLevel) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault()))
        val assessmentData = hashMapOf(
            "level" to level,
            "date" to FieldValue.serverTimestamp()
        )

        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                db.collection("users")
                    .document(userId)
                    .collection("assessments")
                    .document(date)
                    .set(assessmentData)
                    .await()
            } catch (e: Exception) {
                Log.e("AssessmentViewModel", "Error with adding assessment", e)
            } finally {
                _loading.value = false
            }
        }
    }
}