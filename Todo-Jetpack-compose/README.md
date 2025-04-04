 
# Todo Jetpack Compose Lab

This project is a lab app built with **Kotlin**, **Jetpack Compose**, **Retrofit**, and **Coroutines**. It demonstrates how to create a basic Todo app, manage state with a ViewModel, fetch data from an API, and use coroutines for asynchronous tasks.

## Project Structure

```
Todo-Jetpack-compose/
└── lab-todo-compose/
    └── app/
        └── src/
            └── main/
                ├── java/com/example/viewmodel/
                │   ├── MainActivity.kt          // Main screen: Counter & Todo list
                │   ├── LabTodoActivity.kt       // Lab version using Retrofit for API calls
                │   ├── CoroutineDemoActivity.kt // Demo activity using coroutines
                │   ├── CounterViewModel.kt      // ViewModel for managing counter state
                │   ├── data/
                │   │   └── Task.kt              // Data model for tasks
                │   └── network/
                │       ├── ApiService.kt        // Retrofit API interface
                │       └── RetrofitClient.kt    // Retrofit client configuration
                └── res/
                    └── ... (themes, layouts, etc.)
```

## Features

- **Counter with ViewModel:**  
  Demonstrates a simple counter app using a `CounterViewModel` with Kotlin Flow.

- **Todo List:**  
  Displays a list of tasks. Tasks can be fetched from a remote API (using Retrofit) and new tasks can be added locally.

- **API Integration:**  
  Uses Retrofit to fetch tasks from the [JSONPlaceholder API](https://jsonplaceholder.typicode.com/todos).

- **Coroutines Demo:**  
  Uses `LaunchedEffect` and Kotlin coroutines to update UI asynchronously (e.g., a counter that increments every second).

## How to Run

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/samir20-23/KOTLIN.git
   ```
2. **Open in Android Studio:**
   - Open Android Studio and select **"Open an Existing Project"**.
   - Navigate to the cloned repository folder.

3. **Sync Gradle & Build:**
   - Allow Gradle to sync and build the project.
   - Ensure your internet connection is active for Gradle to download required dependencies.

4. **Run the App:**
   - Choose the desired activity (MainActivity, LabTodoActivity, or CoroutineDemoActivity) by setting it as the launcher activity in your `AndroidManifest.xml` or by using Android Studio’s run configuration.
   - Click the **Run** button to install and run the app on an emulator or device.

## Dependencies

- **Jetpack Compose:** For modern UI development.
- **Retrofit & Gson Converter:** For network operations.
- **Kotlin Coroutines:** For handling asynchronous tasks.
- **Android Lifecycle & ViewModel:** For state management.

## Notes

- The API endpoint used is [JSONPlaceholder](https://jsonplaceholder.typicode.com/). You can change the base URL in `RetrofitClient.kt` if needed.
- This project is intended for learning and demonstration purposes.
- Feel free to modify the UI and add more features as you learn.

## License

 
 
## 1. **TUTORIAL🎊**

**File Path:**  
```
KOTLIN\Todo-Jetpack-compose\lab-todo-compose\app\src\main\java\com\example\viewmodel\data\Task.kt
```

**Content:**
```kotlin
package com.example.viewmodel.data

// Using fields provided by jsonplaceholder API: userId, id, title, completed
data class Task(
    val id: Int,
    val title: String,
    val completed: Boolean
)
```

---

## 2. **ApiService.kt**

**File Path:**  
```
KOTLIN\Todo-Jetpack-compose\lab-todo-compose\app\src\main\java\com\example\viewmodel\network\ApiService.kt
```

**Content:**
```kotlin
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
```

---

## 3. **RetrofitClient.kt**

**File Path:**  
```
KOTLIN\Todo-Jetpack-compose\lab-todo-compose\app\src\main\java\com\example\viewmodel\network\RetrofitClient.kt
```

**Content:**
```kotlin
package com.example.viewmodel.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
```

---

## 4. **CoroutineDemoActivity.kt**

**File Path:**  
```
KOTLIN\Todo-Jetpack-compose\lab-todo-compose\app\src\main\java\com\example\viewmodel\CoroutineDemoActivity.kt
```

**Content:**
```kotlin
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
import kotlinx.coroutines.delay

class CoroutineDemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoroutineDemoScreen()
        }
    }
}

@Composable
fun CoroutineDemoScreen() {
    var counter by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            counter++
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Coroutine Demo Counter: $counter",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}
```

---

## 5. **LabTodoActivity.kt**

**File Path:**  
```
KOTLIN\Todo-Jetpack-compose\lab-todo-compose\app\src\main\java\com\example\viewmodel\LabTodoActivity.kt
```

**Content:**
```kotlin
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
```

---

## 6. **MainActivity.kt**

**File Path:**  
```
KOTLIN\Todo-Jetpack-compose\lab-todo-compose\app\src\main\java\com\example\viewmodel\MainActivity.kt
```

**Content:**
```kotlin
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
```

---

## 7. **PreviewLabTodo.kt**

**File Path:**  
```
KOTLIN\Todo-Jetpack-compose\lab-todo-compose\app\src\main\java\com\example\viewmodel\ui\PreviewLabTodo.kt
```

**Content:**
```kotlin
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
```

---

### **Additional File: CounterViewModel.kt**

Since `CounterScreen` references `CounterViewModel`, here’s the complete file:

**File Path:**  
```
KOTLIN\Todo-Jetpack-compose\lab-todo-compose\app\src\main\java\com\example\viewmodel\CounterViewModel.kt
```

**Content:**
```kotlin
package com.example.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CounterViewModel : ViewModel() {
    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count

    fun increment() {
        _count.value++
    }

    fun decrement() {
        _count.value--
    }
}
```
 