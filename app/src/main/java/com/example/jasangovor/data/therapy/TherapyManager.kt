package com.example.jasangovor.data.therapy

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import kotlin.collections.emptyMap

class TherapyManager {
    private val db = Firebase.firestore

    suspend fun getReadingText(): List<ReadingText> {
        val result = db.collection("readingTexts")
            .get()
            .await()
        return result.documents.mapNotNull { doc ->
            doc.toObject(ReadingText::class.java)
        }
    }

    suspend fun getDailyExercises(): Map<String, DailyExercise> {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return emptyMap()

        val result = db.collection("users")
            .document(userId)
            .collection("dailyExercises")
            .get()
            .await()
        return result.documents.associate { doc ->
            val dailyExercise = doc.toObject(DailyExercise::class.java) ?: DailyExercise()
            doc.id to dailyExercise
        }
    }

    suspend fun markExerciseSolved(dayKey: String, exerciseKey: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        db.collection("users")
            .document(userId)
            .collection("dailyExercises")
            .document(dayKey)
            .update("exercises.$exerciseKey.solved", true)
            .await()

        val doc = db.collection("users")
            .document(userId)
            .collection("dailyExercises")
            .document(dayKey)
            .get()
            .await()

        val dailyExercise = doc.toObject(DailyExercise::class.java)
        if (dailyExercise?.exercises?.values?.all { it.solved } == true) {
            db.collection("users")
                .document(userId)
                .collection("dailyExercises")
                .document(dayKey)
                .update("daySolved", true)
                .await()
        }
    }
}