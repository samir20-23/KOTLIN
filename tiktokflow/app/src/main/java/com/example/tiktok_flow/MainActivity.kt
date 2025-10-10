package com.example.tiktok_flow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.tiktok_flow.ui.composables.VideoReel
import com.example.tiktok_flow.ui.theme.TiktokflowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TiktokflowTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    VideoReel(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
