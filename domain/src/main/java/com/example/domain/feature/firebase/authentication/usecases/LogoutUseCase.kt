package com.example.domain.feature.firebase.authentication.usecases

import com.example.domain.feature.firebase.authentication.repository.AuthRepository

class LogoutUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke() = authRepository.logout()
}