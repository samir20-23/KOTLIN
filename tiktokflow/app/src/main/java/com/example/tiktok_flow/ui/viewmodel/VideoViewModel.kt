package com.example.tiktok_flow.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiktok_flow.data.model.User
import com.example.tiktok_flow.data.model.Video
import com.example.tiktok_flow.data.repository.VideoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoViewModel : ViewModel() {
    private val repository = VideoRepository()

    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos

    private val _users = MutableStateFlow<Map<String, User>>(emptyMap())
    val users: StateFlow<Map<String, User>> = _users

    init {
        fetchVideos()
    }

    private fun fetchVideos() {
        viewModelScope.launch {
            val videoList = repository.getVideos()
            _videos.value = videoList
            val userMap = mutableMapOf<String, User>()
            for (video in videoList) {
                if (!userMap.containsKey(video.userId)) {
                    repository.getUser(video.userId)?.let { user ->
                        userMap[video.userId] = user
                    }
                }
            }
            _users.value = userMap
        }
    }

    fun likeVideo(videoId: String, isLiked: Boolean) {
        // TODO: Implement like video functionality
    }

    fun shareVideo(videoId: String) {
        // TODO: Implement share video functionality
    }
}
