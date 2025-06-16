package com.example.matchday

import android.app.Application
import com.example.matchday.di.initKoin

class MatchDayApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(this)
    }
}