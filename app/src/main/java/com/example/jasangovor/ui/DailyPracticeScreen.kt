package com.example.jasangovor.ui

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jasangovor.R
import com.example.jasangovor.data.Exercise
import com.example.jasangovor.presentation.TherapyViewModel
import com.example.jasangovor.ui.theme.BackgroundColor
import com.example.jasangovor.ui.theme.ContainerColor

@Composable
fun DailyPracticeScreen(
    therapyViewModel: TherapyViewModel,
    dayIndex: Int,
    onBackClicked: () -> Unit,
    onExerciseClicked: (exerciseId: Int, dayIndex: Int) -> Unit
) {
    val dailyExercises by therapyViewModel.dailyExercises.collectAsStateWithLifecycle()
    val currentDayKey = "day_$dayIndex"
    val currentDay = dailyExercises[currentDayKey]

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
            DailyPracticeHeader(
                title = "Dnevna vježba",
                onBackClicked = onBackClicked
            )
            Spacer(modifier = Modifier.height(30.dp))

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 25.dp)
            ) {
                currentDay?.exercises
                    ?.entries
                    ?.sortedBy { it.key.substringAfter("_").toIntOrNull() ?: Int.MAX_VALUE }
                    ?.map { it.value }
                    ?.let { exercises ->
                        items(exercises) { exercise ->
                            ExerciseContainer(
                                exercise = exercise,
                                onExerciseClicked = { onExerciseClicked(exercise.id, dayIndex) }
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
        BlackBottomBar()
    }
}

@Composable
fun DailyPracticeHeader(
    title: String,
    onBackClicked: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(ContainerColor)
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { onBackClicked()}) {
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
    }
}

@Composable
fun ExerciseContainer(
    exercise: Exercise,
    onExerciseClicked: () -> Unit
) {
    val activityTypeIcon = when (exercise.type) {
        "introduction" -> R.drawable.ic_introduction
        "quiz" -> R.drawable.ic_quiz
        "exercise" -> R.drawable.ic_exercise
        "conclusion" -> R.drawable.ic_conclusion
        "learn" -> R.drawable.ic_learn
        "reading" -> R.drawable.ic_reading
        else -> R.drawable.ic_lightbulb
    }
    val isActivityDoneIcon = when {
        exercise.solved -> R.drawable.ic_checked
        else -> R.drawable.ic_unchecked
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .background(color = ContainerColor)
            .clickable(
                onClick = { onExerciseClicked() }
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 12.dp)
        ) {
            Image(
                painter = painterResource(id = activityTypeIcon),
                contentDescription = "Activity Icon",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(35.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = exercise.title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Left,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                painter = painterResource(id = isActivityDoneIcon),
                contentDescription = "Activity Done Icon",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}