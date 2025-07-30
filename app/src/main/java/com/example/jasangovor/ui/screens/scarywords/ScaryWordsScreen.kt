package com.example.jasangovor.ui.screens.scarywords

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jasangovor.data.ScaryWord
import com.example.jasangovor.ui.screens.BlackBottomBar
import com.example.jasangovor.ui.screens.StartExerciseButton
import com.example.jasangovor.ui.screens.auth.DefaultHeader
import com.example.jasangovor.ui.theme.BackgroundColor

@Composable
fun ScaryWordsScreen(
    scaryWords: List<ScaryWord>,
    onBackClicked: () -> Unit,
    fetchScaryWords: () -> Unit,
    onAddScaryWord: () -> Unit,
    deleteScaryWord: (String) -> Unit,
    onWordClicked: (String) -> Unit
) {
    LaunchedEffect(Unit) { fetchScaryWords() }

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
                title = "Strašne riječi",
                onBackClicked = onBackClicked
            )
            Spacer(modifier = Modifier.height(25.dp))

            if (scaryWords.isEmpty()) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "Niste unijeli nijednu strašnu riječ",
                        textAlign = TextAlign.Center,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 38.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(120.dp))
                }
            } else {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .weight(1f)
                ) {
                    items(scaryWords) { scaryWord ->
                        WordItem(
                            scaryWord = scaryWord,
                            deleteScaryWord = deleteScaryWord,
                            onWordClicked = {  }
                        )
                    }
                }
            }

            StartExerciseButton(
                title = "Dodaj strašnu riječ",
                onClick = onAddScaryWord
            )
            Spacer(modifier = Modifier.height(30.dp))
        }
        BlackBottomBar()
    }
}

@Composable
fun WordItem(
    scaryWord: ScaryWord,
    deleteScaryWord: (String) -> Unit,
    onWordClicked: (String) -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp)
            .clickable { }
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { showDeleteDialog = true }
                )
            }
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Text(
                text = scaryWord.word,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Obriši strašnu riječ") },
            text = { Text("Jeste li sigurni da želite obrisati ovu riječ") },
            confirmButton = {
                TextButton(
                    onClick = {
                    deleteScaryWord(scaryWord.id)
                    showDeleteDialog = false
                    }
                ) { Text("Obriši") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Odustani") }
            }
        )
    }
}