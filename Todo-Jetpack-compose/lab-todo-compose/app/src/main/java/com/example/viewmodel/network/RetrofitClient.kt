package com.example.viewmodel.network

import com.example.viewmodel.data.Task
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("todos/1") 
    fun getTaskById(): Call<Task>

    @GET("todos") 
    fun getAllTasks(): Call<List<Task>>
}

object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: ApiService = retrofit.create(ApiService::class.java)
}
