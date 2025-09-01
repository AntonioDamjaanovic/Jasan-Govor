package com.example.jasangovor.data.profile

import androidx.core.net.toUri
import com.example.jasangovor.data.scripts.initializeUsersDatabase
import com.example.jasangovor.utils.checkUserInputs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class AuthManager {
    private val auth = FirebaseAuth.getInstance()

    fun checkAuthStatus(): AuthState {
        return if (auth.currentUser == null) {
            AuthState.Unauthenticated
        } else {
            AuthState.Authenticated
        }
    }

    fun login(email: String, password: String, callback: (AuthState) -> Unit) {
        if (email.isEmpty() || password.isEmpty()) {
            callback(AuthState.Error("Email or password can't be empty"))
            return
        }
        callback(AuthState.Loading)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(AuthState.Authenticated)
                } else {
                    callback(AuthState.Error(task.exception?.message ?: "Error happened with login"))
                }
            }
    }

    fun register(
        email: String,
        password: String,
        passwordRepeated: String,
        name: String,
        surname: String,
        callback: (AuthState) -> Unit,
    ) {
        if (!checkUserInputs(name, surname, email, password, passwordRepeated)) {
            callback(AuthState.Error("\"Molimo ispunite sva polja i provjerite jesu li lozinke jednake."))
            return
        }
        callback(AuthState.Loading)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
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
                                callback(AuthState.Authenticated)
                            } else {
                                callback(AuthState.Error("Profile update failed"))
                            }
                        }
                } else {
                    callback(AuthState.Error(task.exception?.message ?: "Error happened with register"))
                }
            }
    }

    fun signOut() {
        auth.signOut()
    }
}