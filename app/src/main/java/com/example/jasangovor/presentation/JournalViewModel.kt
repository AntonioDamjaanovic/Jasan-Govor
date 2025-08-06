package com.example.jasangovor.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jasangovor.data.Note
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

class JournalViewModel: ViewModel() {
    private val db = Firebase.firestore

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun fetchNotes() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = db.collection("users")
                    .document(userId)
                    .collection("journal")
                    .orderBy("date", Query.Direction.DESCENDING)
                    .get()
                    .await()
                val notes = result.documents.mapNotNull { doc ->
                    val text = doc.getString("text") ?: ""
                    val id = doc.id
                    val date = doc.getTimestamp("date")?.toDate()?.time
                    Note(
                        text = text,
                        id = id,
                        date = date
                    )
                }
                _notes.value = notes
            } catch (e: Exception) {
                Log.e("JournalViewModel", "Error with fetching notes", e)
            }
        }
    }

    fun addNote(text: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault()))
        val noteData = hashMapOf(
            "text" to text,
            "date" to FieldValue.serverTimestamp()
        )

        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                db.collection("users")
                    .document(userId)
                    .collection("journal")
                    .document(date)
                    .set(noteData)
                    .await()
            } catch (e: Exception) {
                Log.e("JournalViewModel", "Error with adding or updating note", e)
            } finally {
                _loading.value = false
            }
        }
    }

    fun updateNote(date: String, text: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                db.collection("users")
                    .document(userId)
                    .collection("journal")
                    .document(date)
                    .update("text", text)
                    .await()
            } catch (e: Exception) {
                Log.e("JournalViewModel", "Error with adding or updating note", e)
            } finally {
                _loading.value = false
            }
        }
    }

    fun getNoteById(id: String): Note? {
        return notes.value.find { it.id == id }
    }

    fun deleteNote(date: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                db.collection("users")
                    .document(userId)
                    .collection("journal")
                    .document(date)
                    .delete()
                    .await()
            } catch (e: Exception) {
                Log.e("JournalViewModel", "Error with deleting note", e)
            } finally {
                _loading.value = false
            }
        }
    }
}