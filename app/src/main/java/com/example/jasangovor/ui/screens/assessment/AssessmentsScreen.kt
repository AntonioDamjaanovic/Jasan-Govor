package com.example.jasangovor.ui.screens.assessment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jasangovor.data.StutteringAssessment
import com.example.jasangovor.ui.screens.BlackBottomBar
import com.example.jasangovor.ui.screens.StartExerciseButton
import com.example.jasangovor.ui.screens.auth.DefaultHeader
import com.example.jasangovor.ui.theme.BackgroundColor
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun AssessmentsScreen(
    assessments: List<StutteringAssessment>,
    onBackClicked: () -> Unit,
    fetchAssessments: () -> Unit,
    onTakeAssessment: () -> Unit
) {
    LaunchedEffect(Unit) { fetchAssessments() }

    var currentMonth by remember {
        val calendar = Calendar.getInstance()
        mutableStateOf(
            calendar.get(Calendar.MONTH) to calendar.get(Calendar.YEAR)
        )
    }
    val (month, year) = currentMonth

    val assessmentsThisMonth = remember(assessments, month, year) {
        assessments.filter {
            it.date?.let { timestamp ->
                val calendar = Calendar.getInstance().apply {
                    time = Date(timestamp)
                }
                calendar.get(Calendar.MONTH) == month && calendar.get(Calendar.YEAR) == year
            } ?: false
        }.sortedBy { it.date }
    }

    val assessmentByDay = assessmentsThisMonth.associateBy {
        it.date?.let { day ->
            Calendar.getInstance().apply { time = Date(day) }.get(Calendar.DAY_OF_MONTH)
        }
    }

    val daysInMonth = remember(month, year) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    val monthName = SimpleDateFormat("LLLL yyyy", Locale.getDefault()).format(
        Calendar.getInstance().apply {
            set(Calendar.MONTH, month)
            set(Calendar.YEAR, year)
        }.time
    ).replaceFirstChar { it.uppercaseChar() }

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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(10.dp)
            ) {
                IconButton(
                    onClick = {
                        var m = month - 1
                        var y = year
                        if (m < 0) {
                            m = 11
                            y -= 1
                        }
                        currentMonth = m to y
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                        contentDescription = "Prethodni mjesec",
                        tint = Color.White
                    )
                }
                Text(
                    text = monthName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 18.dp)
                )
                IconButton(
                    onClick = {
                        var m = month + 1
                        var y = year
                        if (m > 11) {
                            m = 0
                            y += 1
                        }
                        currentMonth = m to y
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                        contentDescription = "Sljedeći mjesec",
                        tint = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(5),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 20.dp)
            ) {
                items((1..daysInMonth).toList()) { dayOfMonth ->
                    val assessment = assessmentByDay[dayOfMonth]
                    AssessmentDayCell(
                        day = dayOfMonth,
                        assessment = assessment
                    )
                }
            }

            StartExerciseButton(
                title = "Napravi dnevnu procjenu",
                onClick = onTakeAssessment
            )
            Spacer(modifier = Modifier.height(30.dp))
        }
        BlackBottomBar()
    }
}

@Composable
fun AssessmentDayCell(
    day: Int,
    assessment: StutteringAssessment?
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(6.dp)
            .size(54.dp)
            .background(
                color =
                    if (assessment != null) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = day.toString(),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 20.sp
            )
            if (assessment != null) {
                Spacer(Modifier.height(2.dp))
                Image(
                    painter = painterResource(id = assessment.level.imageRes),
                    contentDescription = assessment.level.displayName,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Spacer(Modifier.height(22.dp))
            }
        }
    }
}