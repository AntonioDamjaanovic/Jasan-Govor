package com.example.jasangovor.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jasangovor.ui.theme.BackgroundColor
import com.example.jasangovor.ui.theme.PinkText
import androidx.compose.runtime.*
import com.example.jasangovor.R
import com.example.jasangovor.ui.theme.GrayButton

@Composable
fun RegisterScreen(

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
            RegisterForm()
        }
    }
}

@Composable
fun RegisterForm(

) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordRepeated by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()

    ) {
        CustomTextField(
            value = name,
            caption = "Ime",
            onValueChange = { name = it },
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
        CustomTextField(                            // TODO hide input
            value = password,
            caption = "Lozinka",
            onValueChange = { password = it },
            iconResId = R.drawable.ic_password
        )
        Spacer(modifier = Modifier.height(20.dp))
        CustomTextField(                            // TODO hide input
            value = passwordRepeated,
            caption = "Ponovi lozinku",
            onValueChange = { passwordRepeated = it },
            iconResId = R.drawable.ic_password
        )
        Spacer(modifier = Modifier.height(30.dp))
        BigGrayButton(title = "Registriraj se", onClick = {})
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    caption: String,
    @DrawableRes iconResId: Int
) {
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
            containerColor = Color(0xFF222222), // dark background
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
        ),
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
fun BigGrayButton(          // TODO make button bigger
    title: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = GrayButton),
        shape = RoundedCornerShape(8.dp),
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