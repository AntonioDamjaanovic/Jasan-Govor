package com.example.jasangovor.data.assessments

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class AssessmentManager {
    private val db = Firebase.firestore

    suspend fun getAssessments(): List<StutteringAssessment> {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return emptyList()

        val result = db.collection("users")
            .document(userId)
            .collection("assessments")
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .await()
        return result.documents.mapNotNull { doc ->
            val level = StutteringLevel.valueOf(doc.getString("level") ?: "NONE")
            val id = doc.id
            val date = doc.getTimestamp("date")?.toDate()?.time
            StutteringAssessment(
                id = id,
                level = level,
                date = date
            )
        }
    }

    suspend fun addAssessment(level: StutteringLevel) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault()))
        val assessmentData = hashMapOf(
            "level" to level,
            "date" to FieldValue.serverTimestamp()
        )

        db.collection("users")
            .document(userId)
            .collection("assessments")
            .document(date)
            .set(assessmentData)
            .await()
    }
}