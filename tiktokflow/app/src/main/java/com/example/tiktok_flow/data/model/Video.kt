package com.example.tiktok_flow.data.model

data class Video(
    val id: String = "",
    val userId: String = "",
    val videoUrl: String = "",
    val thumbnailUrl: String = "",
    val title: String = "",
    val description: String = "",
    val hashtags: List<String> = emptyList(),
    val likes: Int = 0,
    val comments: List<String> = emptyList(),
    val shares: Int = 0,
    val uploadedAt: String = ""
)