package com.example.jasangovor.data.profile

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ProfileManager {
    private val db = Firebase.firestore

    suspend fun getUserProfile(uid: String): UserProfile? {
        val doc = db.collection("users")
            .document(uid)
            .get()
            .await()

        return if (doc.exists()) {
            val email = doc.getString("email") ?: ""
            val username = doc.getString("username") ?: ""
            val dayStreak = doc.getLong("dayStreak")?.toInt() ?: 0
            val lastOpenedDate = doc.getString("lastOpenedDate") ?: ""
            UserProfile(
                email = email,
                username = username,
                dayStreak = dayStreak,
                lastOpenedDate = lastOpenedDate
            )
        } else null
    }

    suspend fun updateDayStreak(uid: String): UserProfile? {
        val userRef = db.collection("users").document(uid)
        val doc = userRef.get().await()

        val zone = ZoneId.systemDefault()
        val today = LocalDate.now(zone)
        val todayDate = today.format(DateTimeFormatter.ISO_LOCAL_DATE)

        val lastOpenedDate = doc.getString("lastOpenedDate") ?: ""
        val dayStreak = doc.getLong("dayStreak")?.toInt() ?: 0
        val lastOpened = lastOpenedDate.takeIf { it.isNotEmpty() }
            ?.let { LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE) }

        val newStreak = when (lastOpened) {
            null -> 1
            today.minusDays(1) -> dayStreak + 1
            today -> dayStreak
            else -> 1
        }

        userRef.update(
            mapOf(
                "lastOpenedDate" to todayDate,
                "dayStreak" to newStreak
            )
        ).await()

        return doc.toObject(UserProfile::class.java)?.copy(
            dayStreak = newStreak,
            lastOpenedDate = todayDate
        )
    }
}