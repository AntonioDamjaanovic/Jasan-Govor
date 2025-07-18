package com.example.jasangovor.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.jasangovor.playback.AndroidAudioPlayer
import com.example.jasangovor.ui.theme.BackgroundColor
import com.example.jasangovor.ui.theme.ContainerColor
import com.example.jasangovor.utils.getAllAudioFiles
import java.io.File

@Composable
fun RecordingsScreen(
    cacheDir: File,
    player: AndroidAudioPlayer,
    onBackClicked: () -> Unit
) {
    var audioFiles by remember { mutableStateOf(getAllAudioFiles(cacheDir)) }

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
            modifier = Modifier
                .weight(1f)
        ) {
            RecordingsListHeader(
                title = "Vaši audio zapisi",
                onBack = {
                    player.stop()
                    onBackClicked()
                }
            )
            if (audioFiles.isEmpty()) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(30.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "Nemate još nijedan zapis. Snimite svoj govor kako biste ga mogli pregledati kasnije.",
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 35.sp,
                        color = Color.White
                    )
                }
            } else {
                var currentlyPlayingFile by remember { mutableStateOf<File?>(null) }

                LazyColumn(modifier = Modifier.padding(25.dp)) {
                    items(audioFiles) { file ->
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
fun RecordingsListHeader(
    title: String,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .background(ContainerColor)
            .padding(horizontal = 20.dp)
    ) {
        IconButton(
            onClick = { onBack() },
            modifier = Modifier.align(Alignment.BottomStart).padding(bottom = 14.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_backarrow),
                contentDescription = "Back Arrow",
                modifier = Modifier.size(45.dp),
                tint = Color.Black
            )
        }
        Text(
            text = title,
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 24.dp)
        )
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