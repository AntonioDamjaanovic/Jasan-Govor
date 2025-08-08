package com.example.jasangovor.ui.screens.record

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.jasangovor.R
import com.example.jasangovor.data.reading.ReadingText
import com.example.jasangovor.record.AndroidAudioRecorder
import com.example.jasangovor.ui.screens.BlackBottomBar
import com.example.jasangovor.ui.screens.StartExerciseButton
import com.example.jasangovor.ui.theme.BackgroundColor
import com.example.jasangovor.ui.theme.ContainerColor
import com.example.jasangovor.utils.startAudioRecording
import java.io.File

@Composable
fun RecordScreen(
    readingTexts: List<ReadingText>,
    selectedTextId: String? = null,
    recorder: AndroidAudioRecorder,
    cacheDir: File,
    onBackClicked: () -> Unit,
    viewRecordings: () -> Unit,
    viewReadingTexts: () -> Unit,
    fetchReadingTexts: () -> Unit,
    getReadingText: (String) -> ReadingText?
) {
    var readingText by remember { mutableStateOf(ReadingText()) }
    val suggestRandomText = {
        if (readingTexts.isNotEmpty()) readingText = readingTexts.random()
    }

    LaunchedEffect(Unit) { fetchReadingTexts() }

    LaunchedEffect(readingTexts, selectedTextId) {
        readingText = when {
            selectedTextId != null && readingTexts.isNotEmpty() ->
                getReadingText(selectedTextId) ?: readingTexts.random()
            readingTexts.isNotEmpty() -> readingTexts.random()
            else -> ReadingText()
        }
    }

    BackHandler {
        recorder.stop()
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
            Column(
                modifier = Modifier.weight(1f)
            ) {
                RecordScreenHeader(
                    title = "Snimi svoj govor",
                    onBack = {
                        recorder.stop()
                        onBackClicked()
                    },
                    onSuggestText = suggestRandomText,
                    viewReadingTexts = viewReadingTexts
                )

                if (readingTexts.isEmpty()) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(horizontal = 30.dp)
                            .fillMaxSize()
                            .weight(1f)
                    ) {
                        Text(
                            text = "Učitavanje...",
                            textAlign = TextAlign.Center,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 38.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(120.dp))
                    }
                } else {
                    ReadingTextBlock(
                        readingText = readingText
                    )
                }
            }
            if (readingTexts.isNotEmpty()) {
                RecordFooter(
                    recorder = recorder,
                    textId = readingText.id,
                    cacheDir = cacheDir,
                    onViewRecordingsClicked = viewRecordings
                )
            }
        }
        BlackBottomBar()
    }
}

@Composable
fun RecordScreenHeader(
    title: String,
    onBack: () -> Unit,
    onSuggestText: () -> Unit,
    viewReadingTexts: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .background(ContainerColor)
            .padding(horizontal = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { onBack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_backarrow),
                    contentDescription = "Back Arrow",
                    modifier = Modifier.size(45.dp),
                    tint = Color.White
                )
            }
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(20.dp))
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StartExerciseButton(
                title = "Odaberi tekst",
                onClick = { viewReadingTexts() }
            )
            StartExerciseButton(
                title = "Predloži tekst",
                onClick = { onSuggestText() }
            )
        }
    }
}

@Composable
fun ReadingTextBlock(
    readingText: ReadingText
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(30.dp)
    ) {
        Text(
            text = readingText.title,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = readingText.text,
            color = Color.White,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 26.sp,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun RecordFooter(
    recorder: AndroidAudioRecorder,
    textId: String,
    cacheDir: File,
    onViewRecordingsClicked: () -> Unit
) {
    var isRecording by remember { mutableStateOf(false) }

    val context = LocalContext.current
    var showPermissionDeniedDialog by remember { mutableStateOf(false) }
    var permissionGranted by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionGranted = granted
        if (granted) {
            startAudioRecording(recorder, cacheDir, textId)
            isRecording = true
        } else {
            showPermissionDeniedDialog = true
        }
    }

    if (showPermissionDeniedDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDeniedDialog = false },
            title = { Text("Potrebno je dopuštenje") },
            text = { Text("Za snimanje zvuka potreban je pristup mikrofonu. Molimo vas da odobrite dopuštenje u postavkama.") },
            confirmButton = {
                TextButton(onClick = {
                    showPermissionDeniedDialog = false
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    context.startActivity(intent)
                }) { Text("Otvori postavke") }
            },
            dismissButton = {
                TextButton(onClick = { showPermissionDeniedDialog = false }) { Text("Odustani") }
            }
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(ContainerColor)
            .padding(horizontal = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable(
                        onClick = {
                            if (isRecording) {
                                recorder.stop()
                                isRecording = false
                            } else {
                                if (permissionGranted) {
                                    startAudioRecording(recorder, cacheDir, textId)
                                    isRecording = true
                                } else {
                                    permissionLauncher.launch(android.Manifest.permission.RECORD_AUDIO)
                                }
                            }
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                val iconRes = if (isRecording) R.drawable.ic_stop else R.drawable.ic_record
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = if (isRecording) "Stop Recording Icon" else "Record Icon",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(60.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(14.dp))
        HorizontalDivider(
            color = Color(0xFFBCAAA4),
            thickness = 2.dp,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = "Prikaži audio zapise",
                color = Color(0xFF2196F3),
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                modifier = Modifier
                    .clickable(
                        onClick = { onViewRecordingsClicked() }
                    )
            )
        }
    }
}