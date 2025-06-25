package com.example.domain.di

import com.example.domain.feature.authentication.usecases.LogoutUseCase
import com.example.domain.feature.authentication.usecases.RegisterUserUseCase
import com.example.domain.feature.authentication.usecases.SignUpEmailPasswordUseCase
import com.example.domain.feature.club.usecases.GetClubByIdUseCase
import com.example.domain.feature.club.usecases.GetClubPlayersUseCase
import com.example.domain.feature.match.usecases.CreateMatchUseCase
import com.example.domain.feature.match.usecases.GetMatchesUseCase
import com.example.domain.feature.player.usecases.AddPlayerToMatchUseCase
import com.example.domain.feature.player.usecases.RegisterPlayerUseCase
import com.example.domain.feature.splash.usecases.CheckUserSessionUseCase
import com.example.domain.feature.user.usecases.GetUserByIdUseCase
import com.example.domain.feature.user.usecases.RegisterAdminUserCase
import org.koin.dsl.module

val domainModule = module {
    factory { RegisterAdminUserCase(get(), get()) }
    factory { RegisterUserUseCase(get()) }
    factory { CreateMatchUseCase(get(), get()) }
    factory { GetClubPlayersUseCase(get()) }
    factory { GetMatchesUseCase(get()) }
    factory { AddPlayerToMatchUseCase(get(), get()) }
    factory { RegisterPlayerUseCase(get(), get()) }
    factory { SignUpEmailPasswordUseCase(get()) }
    factory { CheckUserSessionUseCase(get()) }
    factory { GetUserByIdUseCase(get()) }
    factory { GetClubByIdUseCase(get()) }
    factory { LogoutUseCase(get()) }
}