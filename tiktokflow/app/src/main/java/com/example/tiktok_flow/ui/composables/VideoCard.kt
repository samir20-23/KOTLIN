package com.example.tiktok_flow.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.tiktok_flow.data.model.User
import com.example.tiktok_flow.data.model.Video
import com.example.tiktok_flow.ui.viewmodel.VideoViewModel

@Composable
fun VideoCard(
    video: Video,
    uploader: User?,
    isActive: Boolean,
    modifier: Modifier = Modifier,
    viewModel: VideoViewModel = viewModel()
) {
    var isLiked by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        VideoPlayer(
            videoUri = video.videoUrl,
            isActive = isActive,
            modifier = Modifier.fillMaxSize()
        )
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = uploader?.avatar,
                        contentDescription = "User avatar",
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = uploader?.firstName ?: "Unknown",
                        color = Color.White,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Text(text = video.title, color = Color.White)
                Text(text = video.description, color = Color.White)
            }
            VideoActions(
                likes = video.likes,
                shares = video.shares,
                isLiked = isLiked,
                onLike = {
                    isLiked = !isLiked
                    viewModel.likeVideo(video.id, isLiked)
                },
                onShare = { viewModel.shareVideo(video.id) }
            )
        }
    }
}

@Composable
fun VideoActions(
    likes: Int,
    shares: Int,
    isLiked: Boolean,
    onLike: () -> Unit,
    onShare: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = onLike) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Like",
                tint = if (isLiked) Color.Red else Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
        Text(text = likes.toString(), color = Color.White)
        IconButton(onClick = onShare) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
        Text(text = shares.toString(), color = Color.White)
    }
}