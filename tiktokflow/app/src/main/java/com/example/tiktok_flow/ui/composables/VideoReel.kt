package com.example.tiktok_flow.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun VideoReel(modifier: Modifier = Modifier) {
    val videos = listOf(
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
        "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
    )

    val pagerState = rememberPagerState(pageCount = { videos.size })

    VerticalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize()
    ) { page ->
        VideoPlayer(
            videoUri = videos[page],
            modifier = Modifier.fillMaxSize()
        )
    }
}
