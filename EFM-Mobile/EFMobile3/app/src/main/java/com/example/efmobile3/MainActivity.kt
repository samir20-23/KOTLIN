package com.example.efmobile3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.efmobile3.ui.theme.EFMobile3Theme
import com.example.efmobile3.ui.CoursScreen
import com.example.efmobile3.viewmodel.CoursViewModel

class MainActivity : ComponentActivity() {
    private val viewModel by lazy { CoursViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EFMobile3Theme {
                Surface(modifier = androidx.compose.ui.Modifier.fillMaxSize()) {
                    CoursScreen(viewModel = viewModel)
                }
            }
        }
    }
}