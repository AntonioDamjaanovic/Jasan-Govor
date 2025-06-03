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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.jasangovor.ui.theme.ContainerColor

@Composable
fun RecordScreen(

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
            RecordScreenHeader(title = "Snimi svoj govor")
            Text(
                text = "tekst koji se čita",
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(30.dp)
            )
            RecordFooter()
        }
        BlackBottomBar()
    }
}

@Composable
fun RecordScreenHeader(
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.End
        ) {
            StartExerciseButton(title = "Predloži tekst", onClick = {})
        }
    }
}

@Composable
fun RecordFooter(

) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .background(ContainerColor)
            .padding(horizontal = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable(onClick = {}),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_record),
                    contentDescription = "Record Icon",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(60.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider(
            color = Color(0xFFBCAAA4),
            thickness = 2.dp,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        ) {
            Text(
                text = "Nedavni snimci",
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
            Text(
                text = "Prikaži sve",
                color = Color(0xFF2196F3),
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                modifier = Modifier
                    .clickable(onClick = {})
            )
        }
    }
}