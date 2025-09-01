package com.example.jasangovor.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jasangovor.data.profile.ProfileManager
import com.example.jasangovor.data.profile.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel: ViewModel() {
    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile

    private val profileManager = ProfileManager()

    fun getUserProfile(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                profileManager.getUserProfile(uid)?.let {
                    _userProfile.value = it
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error fetching user profile", e)
            }
        }
    }

    fun onAppOpened(uid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                profileManager.updateDayStreak(uid)?.let {
                    _userProfile.value = it
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error updating day streak", e)
            }
        }
    }
}