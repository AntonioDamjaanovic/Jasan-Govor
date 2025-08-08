package com.example.jasangovor.ui.screens.journal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jasangovor.data.notes.Note
import com.example.jasangovor.ui.screens.BlackBottomBar
import com.example.jasangovor.ui.screens.StartExerciseButton
import com.example.jasangovor.ui.screens.auth.DefaultHeader
import com.example.jasangovor.ui.theme.BackgroundColor
import com.example.jasangovor.utils.filterNotesByDate
import com.example.jasangovor.utils.formatDate
import com.example.jasangovor.utils.formatMillisToDateString
import com.example.jasangovor.utils.getTextPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreen(
    notes: List<Note>,
    fetchNotes: () -> Unit,
    onAddNote: () -> Unit,
    onNoteClicked: (String) -> Unit,
    onBackClicked: () -> Unit,
) {
    LaunchedEffect(Unit) { fetchNotes() }

    var startDate by remember { mutableStateOf<Long?>(null) }
    var endDate by remember { mutableStateOf<Long?>(null) }

    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker by remember { mutableStateOf(false) }
    val startPickerState = rememberDatePickerState(initialSelectedDateMillis = startDate)
    val endPickerState = rememberDatePickerState(initialSelectedDateMillis = endDate)

    val filteredNotes = filterNotesByDate(notes, startDate, endDate)

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
            Spacer(modifier = Modifier.height(25.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Button(onClick = { showStartPicker = true }) {
                    Text(
                        text = if (startDate != null) formatMillisToDateString(startDate)
                            else "Od datuma"
                    )
                }
                Button(onClick = { showEndPicker = true }) {
                    Text(
                        text = if (endDate != null) formatMillisToDateString(endDate)
                        else "Do datuma"
                    )
                }
                if (startDate != null || endDate != null) {
                    IconButton(onClick = { startDate = null; endDate = null }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Makni filtere",
                            tint = Color.White
                        )
                    }
                }
            }

            if (filteredNotes.isEmpty()) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "Nemate bilješki za odabrani period.",
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
                    items(filteredNotes) { note ->
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

    if (showStartPicker) {
        DatePickerDialog(
            onDismissRequest = { showStartPicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        startDate = startPickerState.selectedDateMillis
                        showStartPicker = false
                    }
                ) { Text("Odaberi") }
            },
            dismissButton = {
                TextButton(
                    onClick = { showStartPicker = false }
                ) { Text("Odustani") }
            }
        ) {
            DatePicker(state = startPickerState)
        }
    }
    if (showEndPicker) {
        DatePickerDialog(
            onDismissRequest = { showEndPicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        endDate = endPickerState.selectedDateMillis
                        showEndPicker = false
                    }
                ) { Text("Odaberi") }
            },
            dismissButton = {
                TextButton(
                    onClick = { showEndPicker = false }
                ) { Text("Odustani") }
            }
        ) {
            DatePicker(state = endPickerState)
        }
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
            .padding(top = 25.dp)
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
                text = getTextPreview(note.text),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
