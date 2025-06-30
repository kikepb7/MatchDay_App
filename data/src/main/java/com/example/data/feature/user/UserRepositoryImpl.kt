package com.example.data.feature.user

import com.example.data.feature.user.dto.UserDto
import com.example.data.feature.user.mapper.toUserDto
import com.example.data.feature.user.mapper.toUserModel
import com.example.domain.feature.user.model.UserModel
import com.example.domain.feature.user.repository.UserRepository
import com.example.firebase.firestore.FirebaseFirestoreGenericService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val firestore: FirebaseFirestore
): UserRepository {

    companion object {
        const val USER_PATH = "users"
    }

    private val service = FirebaseFirestoreGenericService(
        firestore = firestore,
        collectionPath = USER_PATH,
        clazz = UserDto::class.java
    )

    override suspend fun createUser(user: UserModel): String = service.createItem(item = user.toUserDto(), key = user.id)

    override suspend fun getAllUsers(): Flow<List<UserModel>> = service.getAllItems().map { list -> list.map { it.toUserModel() } }

    override suspend fun getUserById(userId: String): Flow<UserModel?> = service.getItemById(id = userId).map { it?.toUserModel() }

    override suspend fun updateUser(userId: String, user: UserModel) = service.updateItem(id = userId, item = user.toUserDto())

    override suspend fun deleteUser(userId: String) = service.deleteItem(id = userId)
}