package com.example.domain.feature.firebase.authentication.usecases

import com.example.domain.common.Either
import com.example.domain.common.FailureModel
import com.example.domain.feature.firebase.authentication.repository.AuthRepository

class SignUpEmailPasswordUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Either<FailureModel, String> {
        return authRepository.signUpWithEmailAndPassword(email = email, password = password)
    }
}