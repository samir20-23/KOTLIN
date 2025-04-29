package com.example.myapp.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM User")
    fun getAll(): LiveData<List<User>>

    @Insert
    fun insertAll(vararg users: User)
}
