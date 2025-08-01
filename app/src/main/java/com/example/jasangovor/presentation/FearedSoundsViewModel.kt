package com.example.jasangovor.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jasangovor.data.FearedSound
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FearedSoundsViewModel: ViewModel() {
    private val db = Firebase.firestore

    private val _fearedSounds = MutableStateFlow<List<FearedSound>>(emptyList())
    val fearedSounds: StateFlow<List<FearedSound>> = _fearedSounds

    fun fetchFearedSounds() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = db.collection("fearedSounds")
                    .get()
                    .await()
                val fearedSounds = result.documents.mapNotNull { doc ->
                    doc.toObject(FearedSound::class.java)
                }
                _fearedSounds.value = fearedSounds
            } catch (e: Exception) {
                Log.e("FearedSoundsViewModel", "Error with fetching feared sounds", e)
            }
        }
    }
}