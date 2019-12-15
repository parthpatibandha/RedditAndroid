package com.patibandha.movieapp.di

import android.arch.persistence.room.Room
import com.patibandha.movieapp.data.database.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val roomModule = module {

    single { Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "movies").build() }

    single { get<AppDatabase>().moviesDao() }

}