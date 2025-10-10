package com.example.myfirstapp

import android.content.Context

class TodoRepository(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("todo_prefs", Context.MODE_PRIVATE)
    private val separator = ":::DONE:::"

    fun saveTodoItems(items: List<TodoItem>) {
        val itemStrings = items.map { item ->
            "${item.text}$separator${item.isDone}"
        }.toSet()
        sharedPreferences.edit().putStringSet("todo_items_set", itemStrings).apply()
    }

    fun loadTodoItems(): List<TodoItem> {
        val itemStrings = sharedPreferences.getStringSet("todo_items_set", emptySet()) ?: emptySet()
        return itemStrings.mapNotNull { string ->
            val parts = string.split(separator)
            if (parts.size == 2) {
                val text = parts[0]
                val isDone = parts[1].toBooleanStrictOrNull()
                if (isDone != null) {
                    TodoItem(text, isDone)
                } else {
                    null
                }
            } else {
                null
            }
        }
    }
}