package com.example.viewmodel.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.todojetpackcompose.`interface`.ApiService

object RetrofitClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
