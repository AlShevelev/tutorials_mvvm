package com.mvvm.tutorials.syleiman.mvvmtutorials.model.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.mvvm.tutorials.syleiman.mvvmtutorials.model.Post
import com.mvvm.tutorials.syleiman.mvvmtutorials.model.PostDao

@Database(entities = [Post::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}