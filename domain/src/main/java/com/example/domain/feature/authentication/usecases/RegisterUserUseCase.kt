package com.example.domain.feature.authentication.usecases

import com.example.domain.common.Either
import com.example.domain.common.FailureModel
import com.example.domain.feature.authentication.repository.AuthRepository
import com.example.domain.feature.user.model.UserModel

class RegisterUserUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(user: UserModel): Either<FailureModel, String> {
        return authRepository.registerWithEmailAndPassword(email = user.email, password = user.password, userModel = user)
    }
}