package com.example.jasangovor.ui.screens.fearedsounds

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
import com.example.jasangovor.data.FearedSound
import com.example.jasangovor.ui.screens.BlackBottomBar
import com.example.jasangovor.ui.screens.auth.DefaultHeader
import com.example.jasangovor.ui.theme.BackgroundColor
import com.example.jasangovor.ui.theme.ContainerColor
import com.example.jasangovor.R

@Composable
fun FearedSoundsExercisesScreen(
    fearedSound: FearedSound,
    onExerciseClicked: (String, String) -> Unit,
    onBackClicked: () -> Unit
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
                title = "Vježbe za glas: ${fearedSound.sound}",
                onBackClicked = onBackClicked
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .weight(1f)
            ) {
                FearedSoundExerciseContainer(
                    title = "Vježba Fleksibilne Brzine Govora",
                    onExerciseClicked = { onExerciseClicked(fearedSound.sound, "flexibleRate") }
                )
                Spacer(modifier = Modifier.height(30.dp))
                FearedSoundExerciseContainer(
                    title = "Vježba Tehnike Izlaska",
                    onExerciseClicked = { onExerciseClicked(fearedSound.sound, "pullOuts") }
                )
                Spacer(modifier = Modifier.height(30.dp))
                FearedSoundExerciseContainer(
                    title = "Vježba Pripremnih Postupaka",
                    onExerciseClicked = { onExerciseClicked(fearedSound.sound, "preparatorySets") }
                )
            }
        }
        BlackBottomBar()
    }
}

@Composable
fun FearedSoundExerciseContainer(
    title: String,
    onExerciseClicked: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .background(color = ContainerColor)
            .clickable(onClick = onExerciseClicked)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_exercise),
                contentDescription = "Activity Icon",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(35.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Left,
                modifier = Modifier.weight(1f)
            )
        }
    }
}