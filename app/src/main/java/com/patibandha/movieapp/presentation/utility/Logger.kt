package com.patibandha.movieapp.presentation.utility

import android.util.Log
import com.patibandha.movieapp.BuildConfig

class Logger {

    companion object {
        const val TAG = "AppName"


        fun d(message: String) {
            if (BuildConfig.DEBUG) {
                Log.d(TAG, message)
            }
        }

        fun e(message: String) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, message)
            }
        }

        fun i(message: String) {
            if (BuildConfig.DEBUG) {
                Log.i(TAG, message)
            }
        }

    }
}