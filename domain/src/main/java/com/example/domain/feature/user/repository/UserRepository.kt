package com.example.domain.feature.user.repository

import com.example.domain.feature.user.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun createUser(user: UserModel): String
    suspend fun getAllUsers(): Flow<List<UserModel>>
    suspend fun getUserById(userId: String): Flow<UserModel?>
    suspend fun updateUser(userId: String, user: UserModel)
    suspend fun deleteUser(userId: String)
}