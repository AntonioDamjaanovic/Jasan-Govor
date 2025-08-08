package com.example.jasangovor.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jasangovor.data.profile.UserProfile
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ProfileViewModel: ViewModel() {
    private val db = Firebase.firestore

    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile

    fun fetchUserProfile(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val doc = db.collection("users")
                    .document(uid)
                    .get()
                    .await()

                if (doc.exists()) {
                    val email = doc.getString("email") ?: ""
                    val username = doc.getString("username") ?: ""
                    val dayStreak = doc.getLong("dayStreak")?.toInt() ?: 0
                    val lastOpenedDate = doc.getString("lastOpenedDate") ?: ""
                    _userProfile.value = UserProfile(
                        email = email,
                        username = username,
                        dayStreak = dayStreak,
                        lastOpenedDate = lastOpenedDate
                    )
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error fetching user profile", e)
            }
        }
    }

    fun onAppOpened(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
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

                _userProfile.value = _userProfile.value.copy(
                    dayStreak = newStreak,
                    lastOpenedDate = todayDate
                )

            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error updating day streak", e)
            }
        }
    }
}