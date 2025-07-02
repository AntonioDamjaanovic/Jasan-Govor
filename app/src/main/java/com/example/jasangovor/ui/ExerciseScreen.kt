package com.example.jasangovor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.navigation.NavController
import com.example.jasangovor.Routes
import com.example.jasangovor.data.TherapyViewModel
import com.example.jasangovor.ui.theme.BackgroundColor

@Composable
fun ExerciseScreen(
    navigation: NavController,
    therapyViewModel: TherapyViewModel,
    exerciseId: Int?,
    dayIndex: Int?
) {
    val exercise = remember(exerciseId, dayIndex) {
        therapyViewModel.getExerciseById(exerciseId, dayIndex)
    }
    val steps = exercise?.steps ?: emptyList()
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
            RecordingsListHeader(
                title = "Vježba",
                onBack = {
                    navigation.popBackStack(Routes.SCREEN_DAILY_PRACTICE, false)
                }
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .padding(30.dp)
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
                            exerciseId?.let { id ->
                                therapyViewModel.markExerciseSolved(id)
                            }
                            navigation.popBackStack()
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
    if (step.isNotEmpty()) {
        Text(
            text = step,
            color = Color.White,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 28.sp,
            textAlign = TextAlign.Center
        )
    } else {
        Text(
            text = "Učitavanje...",
            color = Color.White,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            modifier = Modifier.padding(30.dp)
        )
    }
}