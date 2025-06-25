package com.example.domain.feature.authentication.usecases

import com.example.domain.feature.authentication.repository.AuthRepository

class LogoutUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke() = authRepository.logout()
}