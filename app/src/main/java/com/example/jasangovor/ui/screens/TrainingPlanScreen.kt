package com.example.jasangovor.ui.screens

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.jasangovor.R
import com.example.jasangovor.data.DailyExercise
import com.example.jasangovor.presentation.TherapyViewModel
import com.example.jasangovor.ui.theme.BackgroundColor
import com.example.jasangovor.ui.theme.ContainerColor

@Composable
fun TrainingPlanScreen(
    therapyViewModel: TherapyViewModel,
    onBackClicked: () -> Unit,
    onDayClicked: (dayIndex: Int) -> Unit
) {
    therapyViewModel.fetchDailyExercises()
    val dailyExercises by therapyViewModel.dailyExercises.collectAsStateWithLifecycle()
    val sortedDays = dailyExercises.keys.sortedBy {
        it.substringAfter("day_").toIntOrNull() ?: 0
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
            TrainingPlanHeader(
                title = "Vaš plan treninga",
                onBackClicked = onBackClicked
            )
            Spacer(modifier = Modifier.height(30.dp))

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 25.dp)
            ) {
                items(sortedDays.size) { index ->
                    val dayKey = sortedDays[index]
                    val dayNumber = dayKey.substringAfter("day_").toIntOrNull() ?: 1
                    val dailyExercise = dailyExercises[dayKey] ?: DailyExercise()
                    DayContainer(
                        dayLabel = "Day $dayNumber",
                        dailyExercise = dailyExercise,
                        onDayClicked = { onDayClicked(dayNumber) }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
        BlackBottomBar()
    }
}

@Composable
fun TrainingPlanHeader(
    title: String,
    onBackClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .background(ContainerColor)
            .padding(horizontal = 20.dp)
    ) {
        IconButton(
            onClick = { onBackClicked() },
            modifier = Modifier.align(Alignment.BottomStart).padding(bottom = 14.dp)
        ) {
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
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 24.dp)
        )
    }
}

@Composable
fun DayContainer(
    dailyExercise: DailyExercise,
    dayLabel: String,
    onDayClicked: () -> Unit
) {
    val isDayCompletedIcon = when {
        dailyExercise.daySolved -> R.drawable.ic_checked
        else -> R.drawable.ic_unchecked
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .background(color = ContainerColor)
            .clickable(onClick = onDayClicked)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 30.dp)
        ) {
            Text(
                text = dayLabel,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Left,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = isDayCompletedIcon),
                contentDescription = "Activity Done Icon",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}