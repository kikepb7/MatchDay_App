package com.example.ui.di

import com.example.ui.feature.dashboard.DashboardViewModel
import com.example.ui.feature.login.LogoutViewModel
import org.koin.core.module.dsl.viewModel
import com.example.ui.feature.register.RegisterViewModel
import com.example.ui.feature.signup.SignUpViewModel
import com.example.ui.feature.splash.SplashViewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { RegisterViewModel(get(), get(), get()) }
    viewModel { DashboardViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { SignUpViewModel(get(), get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { LogoutViewModel(get()) }
}