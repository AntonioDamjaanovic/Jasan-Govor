package com.example.jasangovor.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.jasangovor.data.ReadingText
import com.example.jasangovor.record.AndroidAudioRecorder
import com.example.jasangovor.ui.theme.BackgroundColor
import java.io.File

@Composable
fun RecordTextScreen(
    recorder: AndroidAudioRecorder,
    cacheDir: File,
    readingText: ReadingText,
    onBackClicked: () -> Unit,
    viewRecordings: () -> Unit,
) {
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
                DefaultHeader(
                    title = "Snimi svoj govor",
                    onBackClicked = onBackClicked
                )
                ReadingTextBlock(
                    readingText = readingText
                )
            }
            RecordFooter(
                recorder = recorder,
                textId = readingText.id,
                cacheDir = cacheDir,
                onViewRecordingsClicked = viewRecordings
            )
        }
        BlackBottomBar()
    }
}