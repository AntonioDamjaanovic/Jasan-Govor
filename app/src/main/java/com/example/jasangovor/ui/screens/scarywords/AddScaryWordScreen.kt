package com.example.jasangovor.ui.screens.scarywords

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jasangovor.ui.screens.BlackBottomBar
import com.example.jasangovor.ui.screens.StartExerciseButton
import com.example.jasangovor.ui.screens.auth.DefaultHeader
import com.example.jasangovor.ui.theme.BackgroundColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScaryWordScreen(
    addScaryWord: (String) -> Unit,
    onBackClicked: () -> Unit
) {
    var scaryWordText by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
        ) {
            DefaultHeader(
                title = "Unesite riječ",
                onBackClicked = onBackClicked
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(30.dp)
                    .weight(1f)
            ) {
                OutlinedTextField(
                    value = scaryWordText,
                    onValueChange = { scaryWordText = it },
                    placeholder = { Text(
                        text = "Unesite riječ ovdje...",
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center
                    ) },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        containerColor = Color(0xFF222222),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                    ),
                    singleLine = false,
                    textStyle = LocalTextStyle.current.copy(fontSize = 22.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                )
            }
            StartExerciseButton(
                title = "Spremi riječ",
                onClick = {
                    if (scaryWordText.isNotBlank()) {
                        addScaryWord(scaryWordText)
                    }
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
        }
        BlackBottomBar()
    }
}