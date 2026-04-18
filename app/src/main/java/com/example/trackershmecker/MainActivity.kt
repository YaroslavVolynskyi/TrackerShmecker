package com.example.trackershmecker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.trackershmecker.navigation.AppNavigation
import com.example.trackershmecker.ui.theme.TrackerShmeckerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrackerShmeckerTheme {
                AppNavigation()
            }
        }
    }
}
