package com.example.myapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.myapp.model.AppDatabase
import com.example.myapp.model.User

class UserViewModel(application: Application) : AndroidViewModel(application) {
    val users: LiveData<List<User>> =
        AppDatabase.getInstance(application).userDao().getAll()
}
