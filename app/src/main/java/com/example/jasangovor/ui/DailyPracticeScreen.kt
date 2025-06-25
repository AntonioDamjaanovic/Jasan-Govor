package com.example.jasangovor.ui

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.jasangovor.data.Exercise
import com.example.jasangovor.data.TherapyViewModel
import com.example.jasangovor.ui.theme.BackgroundColor
import com.example.jasangovor.ui.theme.ContainerColor
import com.example.jasangovor.ui.theme.RoundButtonColor

@Composable
fun DailyPracticeScreen(
    navigation: NavController,
    therapyViewModel: TherapyViewModel
) {
    val dailyExercises by therapyViewModel.dailyExercises.collectAsState()
    val currentDayKey = "day_1"
    val currentDay = dailyExercises[currentDayKey]

    val sortedExercises = currentDay?.exercises
        ?.entries
        ?.sortedBy { it.key.substringAfter("_").toIntOrNull() ?: Int.MAX_VALUE }
        ?.map { it.value }
        ?: emptyList()

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
            DailyPracticeHeader("Dnevna vježba", navigation)
            Spacer(modifier = Modifier.height(30.dp))

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 25.dp)
            ) {
                items(sortedExercises) { exercise ->
                    ExerciseContainer(
                        navigation = navigation,
                        exercise = exercise
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
                item {
                    StartExerciseButton(title = "Nastavi vježbu", onClick = {})
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
    navigation: NavController
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
            IconButton(onClick = { navigation.popBackStack(Routes.SCREEN_HOME, false) }) {
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
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
        ) {
            //TODO check on which day is user currently
            (1..6).forEach { day ->
                RoundButton(
                    day = day,
                    isCompleted = day <= 2,
                    isLocked = day >= 2,
                    onClick = { }
                )
            }
        }
    }
}

@Composable
fun RoundButton(
    day: Int,
    isCompleted: Boolean,
    isLocked: Boolean,
    onClick: () -> Unit
) {
    val dayText = day.toString().padStart(2, '0')
    val iconRes = when {
        isCompleted -> R.drawable.ic_check
        isLocked -> R.drawable.ic_lock
        else -> null
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(RoundButtonColor)
            .clickable(enabled = !isLocked) { onClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(4.dp)
        ) {
            // TODO user did then check, otherwise lock icon
            if (iconRes != null) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(10.dp),
                    tint = Color.Black
                )
            } else {
                Spacer(modifier = Modifier.height(10.dp))
            }
            Text(
                text = "$day",
                color = Color.Black,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ExerciseContainer(
    navigation: NavController,
    exercise: Exercise
) {
    val activityTypeIcon = when (exercise.type) {
        "introduction" -> R.drawable.ic_introduction
        "exercise" -> R.drawable.ic_exercise
        "conclusion" -> R.drawable.ic_conclusion
        "learn" -> R.drawable.ic_learn
        "meditation" -> R.drawable.ic_meditation
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
                onClick = {
                    navigation.navigate(Routes.getExercisePath(exercise.id))
                }
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