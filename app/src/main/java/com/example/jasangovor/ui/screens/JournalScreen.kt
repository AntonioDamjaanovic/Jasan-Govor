package com.example.jasangovor.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jasangovor.data.Note
import com.example.jasangovor.ui.theme.BackgroundColor
import com.example.jasangovor.utils.formatDate

@Composable
fun JournalScreen(
    notes: List<Note>,
    fetchNotes: () -> Unit,
    onAddNote: () -> Unit,
    onNoteClicked: (String) -> Unit,
    onBackClicked: () -> Unit,
) {
    LaunchedEffect(Unit) { fetchNotes() }

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
                title = "Vaše bilješke",
                onBackClicked = onBackClicked
            )
            Spacer(modifier = Modifier.height(30.dp))

            if (notes.isEmpty()) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "Nemate još nijednu bilješku.",
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
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .padding(horizontal = 25.dp)
                        .weight(1f)
                ) {
                    items(notes) { note ->
                        NoteItem(
                            note = note,
                            onNoteClicked = onNoteClicked
                        )
                    }

                }
            }
            StartExerciseButton(
                title = "Dodaj bilješku",
                onClick = onAddNote
            )
            Spacer(modifier = Modifier.height(30.dp))
        }
        BlackBottomBar()
    }
}

@Composable
fun NoteItem(
    note: Note,
    onNoteClicked: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
            .clickable { onNoteClicked(note.id) }
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {
            Text(
                text = formatDate(note.id),
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = note.text,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
