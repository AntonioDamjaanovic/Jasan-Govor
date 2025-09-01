package com.example.jasangovor.presentation

import androidx.lifecycle.ViewModel
import com.example.jasangovor.data.profile.AuthManager
import com.example.jasangovor.data.profile.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {
    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState

    private val authManager = AuthManager()

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        _authState.value = authManager.checkAuthStatus()
    }

    fun login(email: String, password: String) {
        authManager.login(email, password) { state ->
            _authState.value = state
        }
    }

    fun register(email: String, password: String, passwordRepeated: String, name: String, surname: String) {
        authManager.register(email, password, passwordRepeated, name, surname) { state ->
            _authState.value = state
        }
    }

    fun signOut() {
        authManager.signOut()
        checkAuthStatus()
    }

    fun clearAuthState() {
        _authState.value = AuthState.Loading
    }
}