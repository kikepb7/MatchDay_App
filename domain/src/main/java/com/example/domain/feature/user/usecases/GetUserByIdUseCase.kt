package com.example.domain.feature.user.usecases

import com.example.domain.feature.user.model.UserModel
import com.example.domain.feature.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserByIdUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(userId: String): Flow<UserModel?> {
        return userRepository.getUserById(userId = userId)
    }
}