package com.example.domain.feature.authentication.usecases

import com.example.domain.common.Either
import com.example.domain.feature.authentication.repository.AuthRepository

class RegisterUserWithGoogleUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): Either<String, String> =
        authRepository.loginWithGoogle(idToken = idToken)
}