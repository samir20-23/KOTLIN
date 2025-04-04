package com.example.viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viewmodel.network.RetrofitClient
import com.example.viewmodel.data.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LabTodoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LabTodoApp()
        }
    }
}

@Composable
fun LabTodoApp() {
    var taskTitle by remember { mutableStateOf("Loading...") }

    LaunchedEffect(Unit) {
        RetrofitClient.api.getTaskById().enqueue(object : Callback<Task> {
            override fun onResponse(call: Call<Task>, response: Response<Task>) {
                taskTitle = if (response.isSuccessful) {
                    response.body()?.title ?: "Empty response"
                } else {
                    "HTTP error ${response.code()}"
                }
            }
            override fun onFailure(call: Call<Task>, t: Throwable) {
                taskTitle = "Network error: ${t.message}"
            }
        })
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = taskTitle, style = MaterialTheme.typography.headlineMedium)
        }
    }
}
