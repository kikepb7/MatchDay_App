package com.example.domain.feature.splash.usecases

import com.example.domain.common.Either
import com.example.domain.common.FailureModel
import com.example.domain.feature.firebase.authentication.repository.AuthRepository

class CheckUserSessionUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(): Either<FailureModel, Boolean> = authRepository.isUserLogged()
}