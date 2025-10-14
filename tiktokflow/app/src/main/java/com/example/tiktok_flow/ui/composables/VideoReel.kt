package com.example.tiktok_flow.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tiktok_flow.data.model.User
import com.example.tiktok_flow.data.model.Video

@Composable
fun VideoReel(
    videos: List<Video>,
    users: Map<String, User>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        videos.size
    }

    VerticalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize()
    ) { page ->
        val video = videos[page]
        val uploader = users[video.userId]
        VideoCard(
            video = video,
            uploader = uploader,
            isActive = pagerState.currentPage == page,
            modifier = Modifier.fillMaxSize()
        )
    }
}