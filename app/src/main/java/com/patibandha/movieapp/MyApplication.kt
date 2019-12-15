package com.patibandha.movieapp

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.patibandha.movieapp.di.*
import com.facebook.stetho.Stetho
import org.koin.android.ext.android.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        init()
    }

    private fun init() {
        startKoin(
            this,
            arrayListOf(
                networkModule,
                roomModule,
                repositoryModule,
                viewModelModule
            )
        )

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}