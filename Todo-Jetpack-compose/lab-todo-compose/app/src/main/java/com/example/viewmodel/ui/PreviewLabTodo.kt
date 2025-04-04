package com.example.viewmodel.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.viewmodel.LabTodoApp

@Preview(showBackground = true)
@Composable
fun PreviewLabTodo() {
    Surface(color = MaterialTheme.colorScheme.background) {
        LabTodoApp()
    }
}
