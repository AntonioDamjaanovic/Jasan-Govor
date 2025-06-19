package com.example.jasangovor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.jasangovor.data.TherapyViewModel
import com.example.jasangovor.ui.theme.JasanGovorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val therapyViewModel by viewModels<TherapyViewModel>()

        enableEdgeToEdge()
        setContent {
            JasanGovorTheme {
                NavigationController(therapyViewModel)
            }
        }
    }
}
