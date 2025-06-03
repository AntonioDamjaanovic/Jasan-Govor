package com.example.jasangovor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.jasangovor.ui.DailyPracticeScreen
import com.example.jasangovor.ui.RecordScreen
import com.example.jasangovor.ui.theme.JasanGovorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JasanGovorTheme {
                //HomeScreen()
                //RecordScreen()
                DailyPracticeScreen()
            }
        }
    }
}
