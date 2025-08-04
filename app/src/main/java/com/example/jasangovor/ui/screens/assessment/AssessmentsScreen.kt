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
import com.example.jasangovor.utils.filterAssessmentsByMonth
import com.example.jasangovor.utils.formatMonthName
import com.example.jasangovor.utils.getDaysInMonth
import com.example.jasangovor.utils.mapAssessmentsByDay
import java.time.YearMonth
import java.time.ZoneId

@Composable
fun AssessmentsScreen(
    assessments: List<StutteringAssessment>,
    onBackClicked: () -> Unit,
    fetchAssessments: () -> Unit,
    onTakeAssessment: () -> Unit
) {
    LaunchedEffect(Unit) { fetchAssessments() }

    var currentMonth by remember {
        val now = YearMonth.now(ZoneId.systemDefault())
        mutableStateOf(now.monthValue to now.year)
    }
    val (month, year) = currentMonth

    val assessmentsThisMonth = filterAssessmentsByMonth(assessments, month, year)
    val assessmentByDay = mapAssessmentsByDay(assessmentsThisMonth)

    val daysInMonth = remember(month, year) { getDaysInMonth(month, year) }
    val monthName = formatMonthName(month, year)

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
                        val prev = YearMonth.of(year, month).minusMonths(1)
                        currentMonth = prev.monthValue to prev.year
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
                        val next = YearMonth.of(year, month).plusMonths(1)
                        currentMonth = next.monthValue to next.year
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