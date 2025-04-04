package com.example.viewmodel.data

// Using fields provided by jsonplaceholder API: userId, id, title, completed
data class Task(
    val id: Int,
    val title: String,
    val completed: Boolean
)
