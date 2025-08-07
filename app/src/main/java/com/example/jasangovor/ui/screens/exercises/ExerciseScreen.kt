package com.example.jasangovor.ui.screens.exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jasangovor.data.Exercise
import com.example.jasangovor.ui.screens.BlackBottomBar
import com.example.jasangovor.ui.screens.StartExerciseButton
import com.example.jasangovor.ui.screens.auth.DefaultHeader
import com.example.jasangovor.ui.theme.BackgroundColor

@Composable
fun ExerciseScreen(
    exercise: Exercise,
    exerciseId: Int,
    dayIndex: Int,
    onExerciseSolved: (dayIndex: Int, exerciseId: Int) -> Unit,
    onBackClicked: () -> Unit
) {
    val steps = exercise.steps
    var currentStepIndex by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 120.dp)
        ) {
            DefaultHeader(
                title = "Vježba",
                onBackClicked = onBackClicked
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(28.dp)
            ) {
                ExerciseBlock(
                    step = steps.getOrNull(currentStepIndex) ?: "Vježba je gotova"
                )
            }
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                if (currentStepIndex > 0) {
                    StartExerciseButton(
                        title = "Nazad",
                        onClick = {
                            if (currentStepIndex > 0) {
                                currentStepIndex--
                            }
                        }
                    )
                    Spacer(modifier = Modifier.width(50.dp))
                }
                StartExerciseButton(
                    title = if (currentStepIndex < steps.size - 1) "Dalje" else "Završi",
                    onClick = {
                        if (currentStepIndex < steps.size - 1) {
                            currentStepIndex++
                        } else {
                            onExerciseSolved(dayIndex, exerciseId)
                        }
                    }
                )
            }
            BlackBottomBar()
        }
    }
}

@Composable
fun ExerciseBlock(
    step: String
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        if (step.isNotEmpty()) {
            Text(
                text = step,
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                lineHeight = 34.sp,
                textAlign = TextAlign.Center
            )
        } else {
            Text(
                text = "Učitavanje...",
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(30.dp)
            )
        }
    }
}