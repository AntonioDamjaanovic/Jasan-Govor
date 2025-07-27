package com.example.jasangovor.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jasangovor.R
import com.example.jasangovor.data.AuthState
import com.example.jasangovor.ui.theme.BackgroundColor
import com.example.jasangovor.ui.theme.PinkText

@Composable
fun LoginScreen(
    authState: AuthState,
    onLogin: (String, String) -> Unit,
    onRegisterNavigate: () -> Unit,
    onErrorShown: () -> Unit,
    onAuthenticated: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 40.dp, vertical = 100.dp)
        ) {
            BigAppTitle()
            Spacer(modifier = Modifier.height(60.dp))
            LoginForm(
                authState = authState,
                onLogin = onLogin,
                onRegisterNavigate = onRegisterNavigate,
                onErrorShown = onErrorShown,
                onAuthenticated = onAuthenticated
            )
        }
    }
}

@Composable
fun LoginForm(
    authState: AuthState,
    onLogin: (String, String) -> Unit,
    onRegisterNavigate: () -> Unit,
    onErrorShown: () -> Unit,
    onAuthenticated: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> onAuthenticated()
            is AuthState.Error -> {
                Toast.makeText(context, authState.message, Toast.LENGTH_SHORT).show()
                onErrorShown()
            }
            else -> Unit
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        CustomTextField(
            value = email,
            caption = "Email",
            onValueChange = { email = it },
            iconResId = R.drawable.ic_email
        )
        Spacer(modifier = Modifier.height(20.dp))
        CustomTextField(
            value = password,
            caption = "Lozinka",
            onValueChange = { password = it },
            iconResId = R.drawable.ic_password,
            isPassword = true
        )
        Spacer(modifier = Modifier.height(50.dp))
        BigGrayButton(
            title = "Prijavi se",
            onClick = { onLogin(email, password) }
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Nemaš račun? Registriraj se",
            color = PinkText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .clickable(onClick = { onRegisterNavigate() })
        )
    }
}