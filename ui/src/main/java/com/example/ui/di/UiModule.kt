package com.example.ui.di

import com.example.ui.feature.dashboard.DashboardViewModel
import com.example.ui.feature.login.logout.LogoutViewModel
import org.koin.core.module.dsl.viewModel
import com.example.ui.feature.login.register.RegisterViewModel
import com.example.ui.feature.login.signup.SignUpViewModel
import com.example.ui.feature.splash.SplashViewModel
import org.koin.dsl.module

val uiModule = module {
    viewModel { RegisterViewModel(get(), get(), get(), get()) }
    viewModel { DashboardViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { SignUpViewModel(get(), get(), get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { LogoutViewModel(get()) }
}