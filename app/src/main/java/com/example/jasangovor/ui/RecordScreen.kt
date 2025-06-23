package com.example.jasangovor.ui

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jasangovor.R
import com.example.jasangovor.Routes
import com.example.jasangovor.data.ReadingText
import com.example.jasangovor.data.TherapyViewModel
import com.example.jasangovor.record.AndroidAudioRecorder
import com.example.jasangovor.ui.theme.BackgroundColor
import com.example.jasangovor.ui.theme.ContainerColor
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

@Composable
fun RecordScreen(
    navigation: NavController,
    therapyViewModel: TherapyViewModel,
    recorder: AndroidAudioRecorder,
    audioFileState: MutableState<File?>,
    cacheDir: File
) {
    val readingTexts by therapyViewModel.readingTexts.collectAsState()
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
        navigation.popBackStack(Routes.SCREEN_HOME, false)
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
                        navigation.popBackStack(Routes.SCREEN_HOME, false)
                    },
                    onSuggestText = suggestRandomText
                )
                ReadingTextBlock(
                    readingTexts = readingTexts,
                    randomIndex = randomIndex
                )
            }
            RecordFooter(
                navigation = navigation,
                recorder = recorder,
                audioFileState = audioFileState,
                cacheDir = cacheDir
            )
        }
        BlackBottomBar()
    }
}

@Composable
fun RecordScreenHeader(
    title: String,
    onBack: () -> Unit = {},
    onSuggestText: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
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
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_info),
                    contentDescription = "Info Icon",
                    modifier = Modifier.size(40.dp),
                    tint = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
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

        LazyColumn(modifier = Modifier.padding(30.dp)) {
            item {
                Text(
                    text = readingText.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            item {
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
    navigation: NavController,
    recorder: AndroidAudioRecorder,
    audioFileState: MutableState<File?>,
    cacheDir: File
) {
    var isRecording by remember { mutableStateOf(false) }

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
                            isRecording = !isRecording
                            if (isRecording) {
                                val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                                val currentDate = dateFormat.format(Date())
                                val fileName = "audio_$currentDate.mp3"
                                val file = File(cacheDir, fileName)
                                recorder.start(file)
                                audioFileState.value = file
                            } else {
                                recorder.stop()
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
                        onClick = {
                            navigation.navigate(Routes.SCREEN_RECORDINGS)
                        }
                    )
            )
        }
    }
}