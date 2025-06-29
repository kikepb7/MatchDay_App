package com.example.firebase.di

import com.example.firebase.analytics.FirebaseAnalyticsService
import com.example.firebase.authentication.FirebaseAuthService
import com.example.firebase.notification.TopicsService
import com.example.firebase.storage.FirebaseStorageService
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val firebaseModule = module {
    single { FirebaseMessaging.getInstance() }

    single { TopicsService(get()) }
    single<FirebaseAnalytics> { FirebaseAnalytics.getInstance(androidContext()) }
    factory { FirebaseAuthService(get()) }
    factory { FirebaseStorageService(get()) }
    factory { FirebaseAnalyticsService(get()) }
}