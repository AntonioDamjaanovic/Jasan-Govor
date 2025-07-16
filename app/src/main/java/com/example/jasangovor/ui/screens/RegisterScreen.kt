package com.example.jasangovor.ui.screens

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jasangovor.R
import com.example.jasangovor.data.AuthState
import com.example.jasangovor.ui.theme.BackgroundColor
import com.example.jasangovor.ui.theme.GrayButton
import com.example.jasangovor.ui.theme.PinkText

@Composable
fun RegisterScreen(
    authState: AuthState,
    onRegister: (String, String, String, String, String) -> Unit,
    onAuthenticated: () -> Unit,
    onErrorShown: () -> Unit
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
            modifier = Modifier.padding(horizontal = 40.dp, vertical = 100.dp)
        ) {
            BigAppTitle()
            Spacer(modifier = Modifier.height(60.dp))
            RegisterForm(
                authState = authState,
                onRegister = onRegister,
                onAuthenticated = onAuthenticated,
                onErrorShown = onErrorShown
            )
        }
    }
}

@Composable
fun RegisterForm(
    authState: AuthState,
    onRegister: (String, String, String, String, String) -> Unit,
    onAuthenticated: () -> Unit,
    onErrorShown: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordRepeated by remember { mutableStateOf("") }
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
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomTextField(
            value = name,
            caption = "Ime",
            onValueChange = { name = it },
            iconResId = R.drawable.ic_user
        )
        Spacer(modifier = Modifier.height(20.dp))
        CustomTextField(
            value = surname,
            caption = "Prezime",
            onValueChange = { surname = it },
            iconResId = R.drawable.ic_user
        )
        Spacer(modifier = Modifier.height(20.dp))
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
        Spacer(modifier = Modifier.height(20.dp))
        CustomTextField(
            value = passwordRepeated,
            caption = "Ponovi lozinku",
            onValueChange = { passwordRepeated = it },
            iconResId = R.drawable.ic_password,
            isPassword = true
        )
        Spacer(modifier = Modifier.height(50.dp))
        BigGrayButton(
            title = "Registriraj se",
            onClick = {
                onRegister(email, password, passwordRepeated, name, surname)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    caption: String,
    @DrawableRes iconResId: Int,
    isPassword: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = "Text Field Icon",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        },
        placeholder = {
            Text(
                text = caption,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
                )
        },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            containerColor = Color(0xFF222222),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
        ),
        textStyle = TextStyle(
            fontSize = 20.sp,
        ),
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation()
        else VisualTransformation.None,
        trailingIcon = {
            if (isPassword) {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                val description = if (passwordVisible) "Pokaži lozinku" else "Sakrij lozinku"
                IconButton(onClick = { passwordVisible = !passwordVisible} ) {
                    Icon(
                        imageVector = image,
                        contentDescription = description,
                        tint = Color.White
                        )
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun BigAppTitle() {
    Text(
        text = "JASAN GOVOR",
        color = PinkText,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 44.sp,
        maxLines = 1,
        softWrap = false,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun BigGrayButton(
    title: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = GrayButton),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium
        )
    }
}