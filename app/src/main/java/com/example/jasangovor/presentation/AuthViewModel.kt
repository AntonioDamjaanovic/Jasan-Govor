package com.example.jasangovor.presentation

import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jasangovor.data.AuthState
import com.example.jasangovor.data.initializeUsersDatabase
import com.example.jasangovor.utils.checkUserInputs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading)
    val authState: StateFlow<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            _authState.value = AuthState.Authenticated
        }
    }

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password can't be empty")
            return
        }

        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(task.exception?.message?:"Error happened with login")
                }
            }
    }

    fun register(email: String, password: String, passwordRepeated: String, name: String, surname: String) {
        val validInputs = checkUserInputs(name, surname, email, password, passwordRepeated)
        if (!validInputs) {
            _authState.value = AuthState.Error("Molimo ispunite sva polja i provjerite jesu li lozinke jednake.")
            return
        }

        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    initializeUsersDatabase(name, surname, email)
                    val user = auth.currentUser
                    val defaultProfileUrl = "https://cdn-icons-png.flaticon.com/128/3135/3135715.png"

                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName("$name $surname")
                        .setPhotoUri(defaultProfileUrl.toUri())
                        .build()
                    user?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                _authState.value = AuthState.Authenticated
                            } else {
                                _authState.value = AuthState.Error("Profile update failed")
                            }
                        }
                } else {
                    _authState.value = AuthState.Error(task.exception?.message?:"Error happened with register")
                }
            }
    }

    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }

    fun clearAuthState() {
        _authState.value = AuthState.Loading
    }
}