package com.example.simpletodoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simpletodoapp.ui.theme.SimpleTodoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleTodoAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TodoScreen()
                }
            }
        }
    }
}

@Composable
fun TodoScreen() {
    var task by remember { mutableStateOf("") }
    val tasks = remember { mutableStateListOf<String>() }

    Column(modifier = Modifier.padding(16.dp)) {
        BasicTextField(
            value = task,
            onValueChange = { task = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Button(onClick = {
            if (task.isNotBlank()) {
                tasks.add(task)
                task = ""
            }
        }) {
            Text("Add Task")
        }

        Spacer(modifier = Modifier.height(16.dp))

        tasks.forEach { item ->
            Text("- $item")
        }
    }
}
