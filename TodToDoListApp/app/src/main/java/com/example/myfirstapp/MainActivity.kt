package com.example.myfirstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.myfirstapp.ui.theme.MyfirstappTheme

class MainActivity : ComponentActivity() {
    private val todoViewModel by viewModels<TodoViewModel> {
        TodoViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyfirstappTheme {
                TodoListScreen(todoViewModel)
            }
        }
    }
}

@Composable
fun TodoListScreen(viewModel: TodoViewModel) {
    var text by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("New Todo") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                if (text.isNotEmpty()) {
                    viewModel.addTodoItem(text)
                    text = ""
                }
            }) {
                Text("Add")
            }
        }
        LazyColumn {
            items(viewModel.todoItems) { item ->
                val alpha by animateFloatAsState(targetValue = if (item.isDone) 0.5f else 1f)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .alpha(alpha),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = item.text,
                            textDecoration = if (item.isDone) TextDecoration.LineThrough else null
                        )
                        Checkbox(
                            checked = item.isDone,
                            onCheckedChange = { isChecked ->
                                viewModel.changeTodoItemState(item, isChecked)
                            }
                        )
                    }
                }
            }
        }
    }
}