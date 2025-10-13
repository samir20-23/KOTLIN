package com.example.tiktok_flow.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tiktok_flow.ui.viewmodel.VideoViewModel

@Composable
fun VideoReel(modifier: Modifier = Modifier, viewModel: VideoViewModel = viewModel()) {
    val videos by viewModel.videos.collectAsState()
    val users by viewModel.users.collectAsState()

    if (videos.isNotEmpty()) {
        val pagerState = rememberPagerState(pageCount = { videos.size })

        VerticalPager(
            state = pagerState,
            modifier = modifier.fillMaxSize()
        ) { page ->
            val video = videos[page]
            val user = users[video.userId]
            VideoCard(
                video = video,
                uploader = user,
                isActive = pagerState.currentPage == page,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
