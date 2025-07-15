package com.example.jasangovor.ui.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jasangovor.R
import com.example.jasangovor.data.ReadingText
import com.example.jasangovor.presentation.TherapyViewModel
import com.example.jasangovor.record.AndroidAudioRecorder
import com.example.jasangovor.ui.theme.BackgroundColor
import com.example.jasangovor.ui.theme.ContainerColor
import com.example.jasangovor.utils.startAudioRecording
import java.io.File
import kotlin.random.Random


@Composable
fun RecordScreen(
    therapyViewModel: TherapyViewModel,
    recorder: AndroidAudioRecorder,
    cacheDir: File,
    onBackClicked: () -> Unit,
    onViewRecordingsClicked: () -> Unit
) {
    therapyViewModel.fetchReadingTexts()
    val readingTexts by therapyViewModel.readingTexts.collectAsStateWithLifecycle()
    var randomIndex by remember { mutableIntStateOf(0) }

    val suggestRandomText = {
        if (readingTexts.isNotEmpty()) {
            var newIndex: Int
            do {
                newIndex = Random.nextInt(readingTexts.size)
            } while (newIndex == randomIndex)
            randomIndex = newIndex
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
                    onSuggestText = suggestRandomText
                )
                ReadingTextBlock(
                    readingTexts = readingTexts,
                    randomIndex = randomIndex
                )
            }
            if (readingTexts.isNotEmpty()) {
                val textId = readingTexts[randomIndex].id
                RecordFooter(
                    recorder = recorder,
                    textId = textId,
                    cacheDir = cacheDir,
                    onViewRecordingsClicked = onViewRecordingsClicked
                )
            } else {
                Text(
                    text = "Učitavanje...",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(16.dp)
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
    onSuggestText: () -> Unit
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
                    tint = Color.Black
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
            horizontalArrangement = Arrangement.End
        ) {
            StartExerciseButton(
                title = "Predloži tekst",
                onClick = { onSuggestText() }
            )
        }
    }
}

@Composable
fun ReadingTextBlock(
    readingTexts: List<ReadingText>,
    randomIndex: Int
) {
    if (readingTexts.isNotEmpty()) {
        val readingText = readingTexts[randomIndex]
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
    } else {
        Text(
            text = "Učitavanje teksta...",
            color = Color.White,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            modifier = Modifier.padding(30.dp)
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
            .height(160.dp)
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
        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider(
            color = Color(0xFFBCAAA4),
            thickness = 2.dp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        ) {
            Text(
                text = "Prikaži sve audio zapise",
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