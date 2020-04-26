package com.ignacio.cellularautomata

import android.app.Application
import android.content.pm.ApplicationInfo
import android.os.Build
import android.util.Log.DEBUG
import androidx.core.os.BuildCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startup()
    }

    private fun startup() {
        val coroutineScope = CoroutineScope(Dispatchers.Default)
        coroutineScope.launch {
            if(BuildConfig.DEBUG) {
                Timber.plant(Timber.DebugTree())
            }
        }
    }
}