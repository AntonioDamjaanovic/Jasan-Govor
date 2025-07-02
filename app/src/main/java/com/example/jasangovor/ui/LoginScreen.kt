package com.example.jasangovor.ui

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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jasangovor.ui.theme.BackgroundColor
import com.example.jasangovor.R
import com.example.jasangovor.Routes
import com.example.jasangovor.data.TherapyViewModel
import com.example.jasangovor.ui.theme.PinkText

@Composable
fun LoginScreen(
        onLoginClicked: () -> Unit,
        onRegisterClicked: () -> Unit,
        therapyViewModel: TherapyViewModel
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
                onLoginClicked = onLoginClicked,
                onRegisterClicked = onRegisterClicked
            )
        }
    }
}

@Composable
fun LoginForm(
    onLoginClicked: () -> Unit,
    onRegisterClicked: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
            onClick = { onLoginClicked() }
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Nemaš račun? Registriraj se",
            color = PinkText,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .clickable(onClick = { onRegisterClicked() })
        )
    }
}