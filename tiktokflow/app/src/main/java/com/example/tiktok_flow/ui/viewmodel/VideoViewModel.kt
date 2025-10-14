package com.example.tiktok_flow.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tiktok_flow.data.model.Video
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.google.firebase.firestore.Query
import com.example.tiktok_flow.data.model.User
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await

class VideoViewModel : ViewModel() {

    private val _videos = MutableStateFlow<List<Video>>(emptyList())
    val videos: StateFlow<List<Video>> = _videos

    private val _users = MutableStateFlow<Map<String, User>>(emptyMap())
    val users: StateFlow<Map<String, User>> = _users

    private val db = Firebase.firestore

    init {
        fetchVideos()
    }

    private fun fetchVideos() {
        viewModelScope.launch {
            db.collection("videos")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener { result ->
                    val videoList = result.map { document ->
                        document.toObject<Video>().copy(id = document.id)
                    }
                    _videos.value = videoList
                    fetchUsersForVideos(videoList)
                }
                .addOnFailureListener { exception ->
                    // Handle error
                }
        }
    }

    private fun fetchUsersForVideos(videos: List<Video>) {
        viewModelScope.launch {
            val userIds = videos.map { it.userId }.distinct()
            val usersMap = mutableMapOf<String, User>()
            for (userId in userIds) {
                val user = getUser(userId)
                if (user != null) {
                    usersMap[userId] = user
                }
            }
            _users.value = usersMap
        }
    }

    private suspend fun getUser(userId: String): User? {
        return try {
            db.collection("users").document(userId).get().await().toObject<User>()
        } catch (e: Exception) {
            null
        }
    }

    fun likeVideo(videoId: String, isLiked: Boolean) {
        val increment = if (isLiked) 1L else -1L
        db.collection("videos").document(videoId)
            .update("likes", FieldValue.increment(increment))
    }

    fun shareVideo(videoId: String) {
        db.collection("videos").document(videoId)
            .update("shares", FieldValue.increment(1L))
    }
}