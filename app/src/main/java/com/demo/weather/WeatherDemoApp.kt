package com.demo.weather

import android.app.Application
import com.demo.lib.utils.ContextAccessor
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherDemoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ContextAccessor.context = applicationContext
    }
}