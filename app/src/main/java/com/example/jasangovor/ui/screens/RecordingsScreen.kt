package com.example.jasangovor.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jasangovor.R
import com.example.jasangovor.data.ReadingText
import com.example.jasangovor.playback.AndroidAudioPlayer
import com.example.jasangovor.ui.theme.BackgroundColor
import com.example.jasangovor.ui.theme.ContainerColor
import com.example.jasangovor.utils.filterAudioFilesByCategory
import com.example.jasangovor.utils.getAllAudioFiles
import com.example.jasangovor.utils.getReadingTextTitleByCategory
import com.example.jasangovor.utils.getReadingTextsCategories
import java.io.File

@Composable
fun RecordingsScreen(
    cacheDir: File,
    player: AndroidAudioPlayer,
    readingTexts: List<ReadingText>,
    onBackClicked: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf("All") }
    var audioFiles by remember { mutableStateOf(getAllAudioFiles(cacheDir)) }

    val categories = getReadingTextsCategories(readingTexts)
    val filteredFiles = remember(audioFiles, selectedCategory) {
        if (selectedCategory == "All") audioFiles
        else filterAudioFilesByCategory(audioFiles, selectedCategory)
    }

    BackHandler {
        player.stop()
        onBackClicked()
    }

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
            modifier = Modifier.weight(1f)
        ) {
            DefaultHeader (
                title = "Vaši audio zapisi",
                onBackClicked = {
                    player.stop()
                    onBackClicked()
                }
            )

            var expanded by remember { mutableStateOf(false) }
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp, vertical = 30.dp)
            ) {
                val boxWidth = maxWidth
                Row(
                    modifier = Modifier
                        .width(boxWidth)
                        .background(Color(0xFF383838))
                        .clickable { expanded = true }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = when (selectedCategory) {
                            "All" -> "Svi zapisi"
                            else -> getReadingTextTitleByCategory(readingTexts, selectedCategory)
                        },
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Open dropdown",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(boxWidth)
                        .background(Color(0xFF383838))
                ) {
                    categories.forEach {category ->
                        DropdownMenuItem(
                            onClick = {
                                selectedCategory = category
                                expanded = false
                            },
                            text = {
                                Text(
                                    if (category == "All") "Svi zapisi"
                                    else getReadingTextTitleByCategory(readingTexts, category),
                                    color = Color.White,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            },
                            modifier = Modifier
                                .background(Color(0xFF383838))
                                .fillMaxWidth()
                        )
                    }
                }
            }

            if (filteredFiles.isEmpty()) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "Nemate još nijedan zapis. Snimite svoj govor kako biste ga mogli pregledati kasnije.",
                        textAlign = TextAlign.Center,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 38.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(100.dp))
                }
            } else {
                var currentlyPlayingFile by remember { mutableStateOf<File?>(null) }

                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = 25.dp)
                ) {
                    items(filteredFiles) { file ->
                        RecordingContainer(
                            audioFile = file,
                            isPlayingFile = currentlyPlayingFile == file && player.isPlaying(file),
                            onPlay = {
                                player.playFile(file) {
                                    currentlyPlayingFile = null
                                }
                                currentlyPlayingFile = file
                            },
                            onPause = {
                                player.pause()
                                currentlyPlayingFile = null
                            },
                            onDelete = {
                                fileToDelete -> fileToDelete.delete()
                                audioFiles = getAllAudioFiles(cacheDir)
                                if (currentlyPlayingFile == fileToDelete) {
                                    player.stop()
                                    currentlyPlayingFile = null
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
        BlackBottomBar()
    }
}

@Composable
fun RecordingContainer(
    audioFile: File,
    isPlayingFile: Boolean,
    onPlay: () -> Unit,
    onPause: () -> Unit,
    onDelete: (File) -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .background(color = ContainerColor)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { showDeleteDialog = true }
                )
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 15.dp)
        ) {
            Text(
                text = audioFile.name,
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.weight(1f)
            )
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable(
                        onClick = {
                            if (isPlayingFile) {
                                onPause()
                            } else {
                                onPlay()
                            }
                        }
                    )
            ) {
                val iconRes = if (isPlayingFile) R.drawable.ic_stop else R.drawable.ic_play
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = if (isPlayingFile) "Stop Playing Icon" else "Play Icon",
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Obriši audio zapis") },
            text = { Text("Jeste li sigurni da želite obrisati ovaj zapis?") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete(audioFile)
                    showDeleteDialog = false
                }) { Text("Obriši") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Odustani") }
            }
        )
    }
}