package com.example.myapp.model

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "app-db"
                )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Thread {
                            getInstance(context).userDao().insertAll(
                                User(1, "Alice"),
                                User(2, "Bob"),
                                User(3, "Samir")
                            )
                        }.start()
                    }
                })
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
