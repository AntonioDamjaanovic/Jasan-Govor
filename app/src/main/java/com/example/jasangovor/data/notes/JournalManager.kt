package com.example.jasangovor.data.notes

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class JournalManager {
    private val db = Firebase.firestore

    suspend fun getNotes(): List<Note> {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return emptyList()

        val result = db.collection("users")
            .document(userId)
            .collection("journal")
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .await()
        return result.documents.mapNotNull { doc ->
            val text = doc.getString("text") ?: ""
            val id = doc.id
            val date = doc.getTimestamp("date")?.toDate()?.time
            Note(
                text = text,
                id = id,
                date = date
            )
        }
    }

    suspend fun addNote(text: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault()))
        val noteData = hashMapOf(
            "text" to text,
            "date" to FieldValue.serverTimestamp()
        )

        db.collection("users")
            .document(userId)
            .collection("journal")
            .document(date)
            .set(noteData)
            .await()
    }

    suspend fun updateNote(date: String, text: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        db.collection("users")
            .document(userId)
            .collection("journal")
            .document(date)
            .update("text", text)
            .await()
    }

    suspend fun deleteNote(date: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        db.collection("users")
            .document(userId)
            .collection("journal")
            .document(date)
            .delete()
            .await()
    }
}