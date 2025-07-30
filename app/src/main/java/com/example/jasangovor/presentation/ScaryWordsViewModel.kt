package com.example.jasangovor.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jasangovor.data.ScaryWord
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ScaryWordsViewModel: ViewModel() {
    private val db = Firebase.firestore

    private val _scaryWords = MutableStateFlow<List<ScaryWord>>(emptyList())
    val scaryWords: StateFlow<List<ScaryWord>> = _scaryWords

    fun fetchScaryWords() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = db.collection("users")
                    .document(userId)
                    .collection("scary_words")
                    .get()
                    .await()
                val scaryWords = result.documents.mapNotNull { doc ->
                    val word = doc.getString("word") ?: ""
                    val id = doc.id
                    ScaryWord(
                        word = word,
                        id = id
                    )
                }
                _scaryWords.value = scaryWords
            } catch (e: Exception) {
                Log.e("ScaryWordsViewModel", "Error with fetching scary words", e)
            }
        }
    }

    fun addScaryWord(word: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val wordData = hashMapOf(
            "word" to word
        )

        viewModelScope.launch(Dispatchers.IO) {
            try {
                db.collection("users")
                    .document(userId)
                    .collection("scary_words")
                    .document()
                    .set(wordData)
                    .await()
            } catch (e: Exception) {
                Log.e("ScaryWordsViewModel", "Error with adding a scary word", e)
            }
        }
    }

    fun deleteScaryWord(id: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                db.collection("users")
                    .document(userId)
                    .collection("scary_words")
                    .document(id)
                    .delete()
                    .await()
            } catch (e: Exception) {
                Log.e("ScaryWordsViewModel", "Error with deleting a scary word", e)
            }
        }
    }
}