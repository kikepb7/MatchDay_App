package com.example.firebase.di

import com.example.firebase.FirebaseAnalyticsService
import com.example.firebase.FirebaseAuthService
import com.example.firebase.FirebaseStorageService
import org.koin.dsl.module

val firebaseModule = module {
    factory { FirebaseAuthService(get()) }
    factory { FirebaseStorageService(get()) }
    factory { FirebaseAnalyticsService(get()) }
}