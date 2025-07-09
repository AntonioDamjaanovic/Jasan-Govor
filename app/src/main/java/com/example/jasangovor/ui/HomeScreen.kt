package com.example.jasangovor.ui

import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.jasangovor.R
import com.example.jasangovor.ui.theme.BackgroundColor
import com.example.jasangovor.ui.theme.PinkText
import com.example.jasangovor.ui.theme.ButtonTextColor
import com.example.jasangovor.ui.theme.ContainerColor
import com.example.jasangovor.ui.theme.TitleColor

@Composable
fun HomeScreen(
    onStartDailyExerciseClicked: () -> Unit,
    onStartFastExerciseClicked: () -> Unit
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
                .padding(horizontal = 30.dp, vertical = 60.dp)
        ) {
            HomeHeader(dayStreak = 0, onClick = {})
            Spacer(modifier = Modifier.height(50.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    BigBrownContainer(
                        title = "DNEVNA\nVJEŽBA",
                        iconResId = R.drawable.ic_rocket,
                        onClick = { onStartDailyExerciseClicked() }
                        )
                }
                item {
                    Spacer(modifier = Modifier.height(30.dp))
                }
                item {
                    BigBrownContainer(title = "BRZA\nVJEŽBA",
                        iconResId = R.drawable.ic_microphone,
                        onClick = { onStartFastExerciseClicked() }
                        )
                }
                item {
                    Spacer(modifier = Modifier.height(30.dp))
                }
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        SmallBrownContainer(title = "DIŠI I\nOPUSTI SE", iconResId = R.drawable.ic_meditation, onClick = {})
                        SmallBrownContainer(title = "STRAŠNI\nGLASOVI", iconResId = R.drawable.ic_fear, onClick = {})
                        SmallBrownContainer(title = "DNEVNA\nPROCJENA", iconResId = R.drawable.ic_graph, onClick = {})
                    }
                }
            }
        }
        BlackBottomBar()
    }
}

@Composable
fun HomeHeader(
    dayStreak: Int,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "STREAK",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 26.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_flame),
                contentDescription = "Flame Icon",
                modifier = Modifier.size(26.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "$dayStreak",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 26.sp
            )
        }
        Box(
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
                .background(ContainerColor)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.ic_avatar),
                contentDescription = "Avatar Icon",
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEDE4FF)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun BigBrownContainer(
    title: String,
    @DrawableRes iconResId: Int,
    onClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(color = ContainerColor)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 40.dp, end = 40.dp, top = 30.dp)
                .height(80.dp)
        ) {
            Text(
                text = title,
                color = TitleColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp,
                lineHeight = 40.sp
            )
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = "Rocket Icon",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(60.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            StartExerciseButton(
                title = "ZAPOČNI VJEŽBU",
                onClick = onClick
            )
        }
    }
}

@Composable
fun SmallBrownContainer(
    title: String,
    @DrawableRes iconResId: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(120.dp)
            .width(100.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(color = ContainerColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = "Context Icon",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = title,
                color = TitleColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                lineHeight = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(80.dp)
            )
        }
    }
}

@Composable
fun StartExerciseButton(
    title: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = PinkText),
    ) {
        Text(
            text = title,
            color = ButtonTextColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun BlackBottomBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color.Black)
    )
}