package com.example.jasangovor.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.jasangovor.R
import com.example.jasangovor.data.AuthState
import com.example.jasangovor.ui.screens.BlackBottomBar
import com.example.jasangovor.ui.screens.StartExerciseButton
import com.example.jasangovor.ui.theme.BackgroundColor
import com.example.jasangovor.ui.theme.ContainerColor
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(
    authState: AuthState,
    userEmail: String,
    dayStreak: Int,
    onBackClicked: () -> Unit,
    signOut: () -> Unit,
    onSignOutClicked: () -> Unit
) {
    val user = FirebaseAuth.getInstance().currentUser
    val userName = user?.displayName ?: ""
    val photoUrl = user?.photoUrl?.toString()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Unauthenticated -> onSignOutClicked()
            else -> Unit
        }
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
            DefaultHeader(
                title = "Vaš profil",
                onBackClicked = onBackClicked
            )
            Spacer(modifier = Modifier.height(30.dp))
            ProfilePictureContainer(
                photoUrl = photoUrl,
                userName =  userName
            )
            ProfileDetails(
                email = userEmail,
                dayStreak = dayStreak
            )
            Spacer(modifier = Modifier.height(30.dp))
            StartExerciseButton(
                title = "Odjavi se",
                onClick = { signOut() }
            )
        }
        BlackBottomBar()
    }
}

@Composable
fun DefaultHeader(
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
            onClick = onBackClicked,
            modifier = Modifier.align(Alignment.BottomStart).padding(bottom = 14.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_backarrow),
                contentDescription = "Back Arrow",
                modifier = Modifier.size(45.dp),
                tint = Color.Black,
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
fun ProfilePictureContainer(
    photoUrl: String?,
    userName: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (photoUrl.isNullOrEmpty()) {
            Image(
                painter = painterResource(id = R.drawable.ic_avatar),
                contentDescription = "Default Profile Picture",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(80.dp)
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(photoUrl),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = userName,
            color = Color.White,
            fontSize = 30.sp
        )
    }
}

@Composable
fun ProfileDetails(
    email: String,
    dayStreak: Int
) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(30.dp)
    ) {
        Text(
            text = "Vaše informacije",
            color = Color.White,
            fontSize = 22.sp,
            textAlign = TextAlign.Left
        )
        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = "Day Streak: $dayStreak",
            color = Color.White,
            fontSize = 18.sp,
            textAlign = TextAlign.Left
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Email: $email",
            color = Color.White,
            fontSize = 18.sp,
            textAlign = TextAlign.Left
        )
    }
}
