package com.example.domain.feature.authentication.repository

import com.example.domain.common.Either
import com.example.domain.common.FailureModel
import com.example.domain.feature.user.model.UserModel

interface AuthRepository {
    suspend fun signUpWithEmailAndPassword(email: String, password: String): Either<FailureModel, String>
    suspend fun registerWithEmailAndPassword(email: String, password: String, userModel: UserModel): Either<FailureModel, String>
    suspend fun isUserLogged(): Either<FailureModel, Boolean>
    suspend fun logout()
}