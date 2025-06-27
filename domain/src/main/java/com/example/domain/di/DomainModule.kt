package com.example.domain.di

import com.example.domain.feature.authentication.usecases.LogoutUseCase
import com.example.domain.feature.authentication.usecases.RegisterUserUseCase
import com.example.domain.feature.authentication.usecases.RegisterUserWithGoogleUseCase
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
import com.example.domain.feature.user.usecases.UploadUserImageUseCase
import org.koin.dsl.module

val domainModule = module {

    // Auth / User registration
    factory { RegisterUserUseCase(get()) }
    factory { RegisterAdminUserCase(get(), get()) }
    factory { RegisterPlayerUseCase(get(), get()) }
    factory { RegisterUserWithGoogleUseCase(get()) }
    factory { SignUpEmailPasswordUseCase(get()) }

    // Session
    factory { CheckUserSessionUseCase(get()) }
    factory { LogoutUseCase(get()) }

    // Image
    factory { UploadUserImageUseCase(get()) }

    // User / Club queries
    factory { GetUserByIdUseCase(get()) }
    factory { GetClubByIdUseCase(get()) }
    factory { GetClubPlayersUseCase(get()) }

    // Match-related
    factory { CreateMatchUseCase(get(), get()) }
    factory { GetMatchesUseCase(get()) }
    factory { AddPlayerToMatchUseCase(get(), get()) }
}