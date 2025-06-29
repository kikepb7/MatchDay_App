package com.example.matchday

import android.app.Application
import com.example.matchday.di.initKoin
import com.google.firebase.FirebaseApp

class MatchDayApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(this)
        FirebaseApp.initializeApp(this)
    }
}