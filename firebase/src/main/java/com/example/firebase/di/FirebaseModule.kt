package com.example.firebase.di

import com.example.firebase.analytics.FirebaseAnalyticsService
import com.example.firebase.authentication.FirebaseAuthService
import com.example.firebase.notification.TopicsService
import com.example.firebase.storage.FirebaseStorageService
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val firebaseModule = module {
    single { Firebase.firestore }
    single { Firebase.database.reference }
    single { FirebaseMessaging.getInstance() }
    single { TopicsService(get()) }
    factory { FirebaseAuthService(get()) }
    factory { FirebaseStorageService(get()) }
    factory { FirebaseAnalyticsService(get()) }
    single<FirebaseAnalytics> { FirebaseAnalytics.getInstance(androidContext()) }
}