package com.example.data.di

import com.example.data.feature.firebase.FirebaseAuthRepositoryImpl
import com.example.data.feature.club.ClubRepositoryImpl
import com.example.data.feature.firebase.FirebaseAnalyticsRepositoryImpl
import com.example.data.feature.firebase.FirebaseStorageRepositoryImpl
import com.example.data.feature.match.MatchRepositoryImpl
import com.example.data.feature.player.PlayerRepositoryImpl
import com.example.data.feature.statistics.StatisticsRepositoryImpl
import com.example.data.feature.user.UserRepositoryImpl
import com.example.domain.feature.analytics.repository.AnalyticsRepository
import com.example.domain.feature.authentication.repository.AuthRepository
import com.example.domain.feature.club.repository.ClubRepository
import com.example.domain.feature.match.repository.MatchRepository
import com.example.domain.feature.player.repository.PlayerRepository
import com.example.domain.feature.statistics.repository.StatisticsRepository
import com.example.domain.feature.user.repository.UploadImageRepository
import com.example.domain.feature.user.repository.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database
import com.google.firebase.storage.storage
import org.koin.dsl.module

val dataModule = module {

    // Firebase
    single { Firebase.database.reference }
    single { Firebase.storage }
    single { FirebaseAuth.getInstance() }


    // Repositories
    factory<UploadImageRepository> { FirebaseStorageRepositoryImpl(get()) }
    factory<UserRepository> { UserRepositoryImpl(get()) }
    factory<PlayerRepository> { PlayerRepositoryImpl(get()) }
    factory<MatchRepository> { MatchRepositoryImpl(get()) }
    factory<StatisticsRepository> { StatisticsRepositoryImpl(get()) }
    factory<ClubRepository> { ClubRepositoryImpl(get()) }
    factory<AnalyticsRepository> { FirebaseAnalyticsRepositoryImpl(get()) }
    factory<AuthRepository> { FirebaseAuthRepositoryImpl(get(), get()) }
}