package com.example.jasangovor.ui.data

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class TherapyViewModel: ViewModel() {
    private val db = Firebase.firestore

    private val _readingTexts = MutableStateFlow<List<ReadingText>>(emptyList())
    val readingTexts: StateFlow<List<ReadingText>> = _readingTexts

    init {
        fetchDatabaseData()
    }

    private fun fetchDatabaseData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = db.collection("readingTexts").get().await()
                val texts = result.documents.mapNotNull {
                    it.toObject(ReadingText::class.java) }
                _readingTexts.value = texts
            } catch (e: Exception) {
                Log.e("TherapyViewModel", "Firestore error", e)
            }
        }
    }
}