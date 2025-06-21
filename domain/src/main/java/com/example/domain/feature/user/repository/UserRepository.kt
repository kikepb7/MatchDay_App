package com.example.domain.feature.user.repository

import com.example.domain.feature.user.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun createUser(user: UserModel): String
    fun getAllUsers(): Flow<List<UserModel>>
    fun getUserById(userId: String): Flow<UserModel?>
    fun updateUser(userId: String, user: UserModel)
    fun deleteUser(userId: String)
}