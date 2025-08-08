package com.example.jasangovor.ui.screens.exercises

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jasangovor.R
import com.example.jasangovor.data.exercises.Exercise
import com.example.jasangovor.data.displays.ExerciseDisplay
import com.example.jasangovor.ui.screens.BlackBottomBar
import com.example.jasangovor.ui.screens.auth.DefaultHeader
import com.example.jasangovor.ui.theme.BackgroundColor
import com.example.jasangovor.ui.theme.ContainerColor

@Composable
fun DailyPracticeScreen(
    exerciseDisplays: List<ExerciseDisplay>,
    dayIndex: Int,
    onBackClicked: () -> Unit,
    onExerciseClicked: (exerciseId: Int, dayIndex: Int) -> Unit
) {
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
                title = "Dnevna vježba",
                onBackClicked = onBackClicked
            )
            Spacer(modifier = Modifier.height(30.dp))

            if (exerciseDisplays.isEmpty()) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
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
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = 25.dp)
                ) {
                    items(exerciseDisplays) { display ->
                        ExerciseContainer(
                            exercise = display.exercise,
                            locked = display.locked,
                            onExerciseClicked = { onExerciseClicked(display.exercise.id, dayIndex) },
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }
        }
        BlackBottomBar()
    }
}

@Composable
fun ExerciseContainer(
    exercise: Exercise,
    locked: Boolean,
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
        locked -> R.drawable.ic_lock
        else -> R.drawable.ic_unchecked
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .background(color = ContainerColor)
            .clickable(onClick = onExerciseClicked, enabled = !locked)
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