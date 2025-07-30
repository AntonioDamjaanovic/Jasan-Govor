package com.example.jasangovor.ui.screens.assessment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.jasangovor.data.StutteringLevel
import com.example.jasangovor.ui.screens.BlackBottomBar
import com.example.jasangovor.ui.screens.StartExerciseButton
import com.example.jasangovor.ui.screens.auth.DefaultHeader
import com.example.jasangovor.ui.theme.BackgroundColor
import kotlin.math.roundToInt

@Composable
fun DailyAssessmentScreen(
    onBackClicked: () -> Unit,
    takeAssessment: (StutteringLevel) -> Unit
) {
    val levels = StutteringLevel.entries.toTypedArray()
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    val selectedLevel = levels[sliderPosition.toInt()]

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
                title = "Dnevna procjena",
                onBackClicked = onBackClicked
            )
            Spacer(modifier = Modifier.height(30.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(vertical = 30.dp)
                    .weight(1f)
            ) {
                Image(
                    painter = painterResource(id = selectedLevel.imageRes),
                    contentDescription = selectedLevel.displayName,
                    modifier = Modifier.size(140.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = selectedLevel.displayName,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(20.dp))
                Slider(
                    value = sliderPosition,
                    onValueChange = { sliderPosition = it.roundToInt().toFloat() },
                    steps = levels.size - 2,
                    valueRange = 0f..(levels.size - 1).toFloat(),
                    modifier = Modifier.width(260.dp)
                )
            }

            StartExerciseButton(
                title = "Spremi procjenu",
                onClick = { takeAssessment(selectedLevel) }
            )
            Spacer(modifier = Modifier.height(30.dp))
        }
        BlackBottomBar()
    }
}