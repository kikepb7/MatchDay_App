package com.example.auth.di

import com.example.auth.FirebaseAuthService
import org.koin.dsl.module

val authModule = module {
    factory { FirebaseAuthService(get()) }
}