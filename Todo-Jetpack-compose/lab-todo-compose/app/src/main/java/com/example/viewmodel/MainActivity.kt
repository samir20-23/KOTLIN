package com.example.viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viewmodel.network.RetrofitClient
import com.example.viewmodel.data.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityUI()
        }
    }
}

@Composable
fun MainActivityUI() {
    Column(modifier = Modifier.padding(16.dp)) {
        CounterScreen()
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        TodoApp()
    }
}

@Composable
fun CounterScreen(counterViewModel: CounterViewModel = viewModel()) {
    val count by counterViewModel.count.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Counter: $count", style = MaterialTheme.typography.headlineMedium)
        Row(modifier = Modifier.padding(16.dp)) {
            Button(onClick = { counterViewModel.decrement() }, modifier = Modifier.padding(8.dp)) {
                Text("-")
            }
            Button(onClick = { counterViewModel.increment() }, modifier = Modifier.padding(8.dp)) {
                Text("+")
            }
        }
    }
}

@Composable
fun TodoApp() {
    var todos by remember { mutableStateOf<List<Task>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var newTask by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        RetrofitClient.api.getAllTasks().enqueue(object : Callback<List<Task>> {
            override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                if (response.isSuccessful) {
                    todos = response.body() ?: emptyList()
                } else {
                    errorMessage = "HTTP error ${response.code()}"
                }
            }
            override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                errorMessage = "Network error: ${t.message}"
            }
        })
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Todo List", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            TextField(
                value = newTask,
                onValueChange = { newTask = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Enter task") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (newTask.isNotBlank()) {
                    // Create a new Task instance manually
                    val task = Task(
                        id = todos.size + 1,
                        title = newTask,
                        completed = false
                    )
                    todos = todos + task
                    newTask = ""
                }
            }) {
                Text("Add")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (errorMessage != null) {
            Text(text = errorMessage!!, color = MaterialTheme.colorScheme.error)
        }
        LazyColumn {
            items(todos) { task ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = task.title, style = MaterialTheme.typography.bodyLarge)
                        Text(
                            text = if (task.completed) "Completed" else "Pending",
                            color = if (task.completed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}
