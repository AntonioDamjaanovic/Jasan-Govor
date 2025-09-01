package com.example.jasangovor.data.fearedsounds

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class FearedSoundManager {
    private val db = Firebase.firestore

    suspend fun getFearedSounds(): List<FearedSound> {
        val result = db.collection("fearedSounds")
            .get()
            .await()
        return result.documents.mapNotNull { doc ->
            doc.toObject(FearedSound::class.java)
        }
    }
}