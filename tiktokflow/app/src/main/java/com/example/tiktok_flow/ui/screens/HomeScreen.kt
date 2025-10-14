package com.example.tiktok_flow.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tiktok_flow.ui.composables.VideoReel
import com.example.tiktok_flow.ui.viewmodel.VideoViewModel

@Composable
fun HomeScreen(
    viewModel: VideoViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val videos by viewModel.videos.collectAsState()
    val users by viewModel.users.collectAsState()

    if (videos.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        VideoReel(
            videos = videos,
            users = users,
            modifier = modifier
        )
    }
}
