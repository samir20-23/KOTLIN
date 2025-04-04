package com.example.viewmodel.network

import com.example.viewmodel.data.Task
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("todos/1") // Fetch one task
    fun getTaskById(): Call<Task>

    @GET("todos") // Fetch all tasks
    fun getAllTasks(): Call<List<Task>>
}
