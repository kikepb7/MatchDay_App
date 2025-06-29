package com.example.domain.di

import com.example.domain.feature.firebase.analytics.usecases.LogAnalyticsEventUseCase
import com.example.domain.feature.firebase.authentication.usecases.LogoutUseCase
import com.example.domain.feature.firebase.authentication.usecases.RegisterUserUseCase
import com.example.domain.feature.firebase.authentication.usecases.RegisterUserWithGoogleUseCase
import com.example.domain.feature.firebase.authentication.usecases.SignUpEmailPasswordUseCase
import com.example.domain.feature.club.usecases.GetClubByIdUseCase
import com.example.domain.feature.club.usecases.GetClubPlayersUseCase
import com.example.domain.feature.firebase.notification.usecases.IsSubscribedToMatchTopicUseCase
import com.example.domain.feature.firebase.notification.usecases.SubscribeToMatchTopicUseCase
import com.example.domain.feature.firebase.notification.usecases.UnsubscribeFromMatchTopicUseCase
import com.example.domain.feature.match.usecases.CreateMatchUseCase
import com.example.domain.feature.match.usecases.GetMatchesUseCase
import com.example.domain.feature.player.usecases.AddPlayerToMatchUseCase
import com.example.domain.feature.player.usecases.RegisterPlayerUseCase
import com.example.domain.feature.splash.usecases.CheckUserSessionUseCase
import com.example.domain.feature.user.usecases.GetAllClubUsersUseCase
import com.example.domain.feature.user.usecases.GetUserByIdUseCase
import com.example.domain.feature.user.usecases.RegisterAdminUserCase
import com.example.domain.feature.user.usecases.UploadUserImageUseCase
import org.koin.dsl.module

val domainModule = module {

    // Auth / User registration
    factory { RegisterUserUseCase(get()) }
    factory { RegisterAdminUserCase(get(), get(), get()) }
    factory { RegisterPlayerUseCase(get(), get()) }
    factory { RegisterUserWithGoogleUseCase(get()) }
    factory { SignUpEmailPasswordUseCase(get()) }
    factory { LogAnalyticsEventUseCase(get()) }

    // Session
    factory { CheckUserSessionUseCase(get()) }
    factory { LogoutUseCase(get()) }

    // Image
    factory { UploadUserImageUseCase(get()) }

    // User / Club queries
    factory { GetUserByIdUseCase(get()) }
    factory { GetClubByIdUseCase(get()) }
    factory { GetClubPlayersUseCase(get()) }
    factory { GetAllClubUsersUseCase(get(), get(), get()) }

    // Match-related
    factory { CreateMatchUseCase(get(), get()) }
    factory { GetMatchesUseCase(get()) }
    factory { AddPlayerToMatchUseCase(get(), get()) }

    // Notifications
    factory { IsSubscribedToMatchTopicUseCase(get()) }
    factory { SubscribeToMatchTopicUseCase(get()) }
    factory { UnsubscribeFromMatchTopicUseCase(get()) }
}