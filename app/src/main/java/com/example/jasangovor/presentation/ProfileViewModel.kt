package com.example.jasangovor.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jasangovor.data.UserProfile
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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
                    val dayStreak = doc.getLong("dayStreak")?.toInt() ?: 0
                    _userProfile.value = UserProfile(email = email, dayStreak = dayStreak)
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error fetching user profile", e)
            }
        }
    }
}