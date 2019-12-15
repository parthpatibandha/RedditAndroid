package com.patibandha.movieapp.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.patibandha.movieapp.data.models.RedditPopularItem

@Database(entities = [RedditPopularItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}