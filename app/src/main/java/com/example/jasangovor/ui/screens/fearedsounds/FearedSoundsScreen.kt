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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jasangovor.R
import com.example.jasangovor.data.fearedsounds.FearedSound
import com.example.jasangovor.ui.screens.BlackBottomBar
import com.example.jasangovor.ui.screens.auth.DefaultHeader
import com.example.jasangovor.ui.screens.exercises.LoadingBlock
import com.example.jasangovor.ui.theme.BackgroundColor
import com.example.jasangovor.ui.theme.ContainerColor

@Composable
fun FearedSoundsScreen(
    fearedSounds: List<FearedSound>,
    getFearedSounds: () -> Unit,
    onBackClicked: () -> Unit,
    onSoundClicked: (String) -> Unit
) {
    LaunchedEffect(Unit) { getFearedSounds() }

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
                title = "Strašni glasovi",
                onBackClicked = onBackClicked
            )
            Spacer(modifier = Modifier.height(30.dp))

            if (fearedSounds.isEmpty()) {
                LoadingBlock()
            } else {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .weight(1f)
                ) {
                    items(fearedSounds) { fearedSound ->
                        SoundContainer(
                            fearedSound = fearedSound,
                            onSoundClicked = onSoundClicked
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                    }
                }
            }
        }
        BlackBottomBar()
    }
}

@Composable
fun SoundContainer(
    fearedSound: FearedSound,
    onSoundClicked: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .background(color = ContainerColor)
            .clickable { onSoundClicked(fearedSound.sound) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 30.dp)
        ) {
            Text(
                text = "Glas: ${fearedSound.sound}",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Left,
                modifier = Modifier.weight(1f)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_exercise),
                contentDescription = "Sound Exercise",
                modifier = Modifier.size(40.dp)
            )
        }
    }
}