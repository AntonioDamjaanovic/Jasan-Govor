package com.example.jasangovor.ui

import android.graphics.Paint.Align
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jasangovor.R
import com.example.jasangovor.ui.theme.BackgroundColor
import com.example.jasangovor.ui.theme.ContainerColor
import com.example.jasangovor.ui.theme.RoundButtonColor

@Composable
fun DailyPracticeScreen(

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
            DailyPracticeHeader("Dnevna vježba")
        }
        BlackBottomBar()
    }


}

@Composable
fun DailyPracticeHeader(
    title: String
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
            IconButton(onClick = {}) {
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
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
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
            .size(44.dp)
            .clip(CircleShape)
            .background(RoundButtonColor)
            .clickable(enabled = !isLocked) {onClick()}
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // TODO user did then check, otherwise lock icon
            if (iconRes != null) {
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = Color.Black
                )
            } else {
                Spacer(modifier = Modifier.height(12.dp))
            }
            Text(
                text = "$day",
                color = Color.Black,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }

    }
}