package com.example.jasangovor.ui.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class TherapyViewModel: ViewModel() {
    private val db = Firebase.firestore

    init {
        fetchDatabaseData()
    }

    private fun fetchDatabaseData() {
        db.collection("readingTexts")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
}