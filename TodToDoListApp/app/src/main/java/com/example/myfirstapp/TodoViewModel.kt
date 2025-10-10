package com.example.myfirstapp

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {
    private val _todoItems = mutableStateListOf<TodoItem>()
    val todoItems: List<TodoItem> = _todoItems

    init {
        _todoItems.addAll(repository.loadTodoItems())
    }

    fun addTodoItem(text: String) {
        _todoItems.add(TodoItem(text))
        repository.saveTodoItems(_todoItems)
    }

    fun changeTodoItemState(item: TodoItem, isDone: Boolean) {
        val index = _todoItems.indexOf(item)
        if (index >= 0) {
            _todoItems[index] = item.copy(isDone = isDone)
            repository.saveTodoItems(_todoItems)
        }
    }
}
