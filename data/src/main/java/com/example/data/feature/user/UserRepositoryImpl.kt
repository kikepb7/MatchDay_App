package com.example.data.feature.user

import com.example.data.feature.user.dto.UserDto
import com.example.data.feature.user.mapper.toUserDto
import com.example.data.feature.user.mapper.toUserModel
import com.example.data.firebase.FirebaseGenericService
import com.example.domain.feature.user.model.UserModel
import com.example.domain.feature.user.repository.UserRepository
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val reference: DatabaseReference
): UserRepository {

    companion object {
        const val USER_PATH = "users"
    }

    private val service = FirebaseGenericService(
        reference = reference,
        basePath = USER_PATH,
        clazz = UserDto::class.java
    )

    override fun createUser(user: UserModel): String = service.createItem(item = user.toUserDto())

    override fun getAllUsers(): Flow<List<UserModel>> = service.getAllItems().map { list -> list.map { it.toUserModel() } }

    override fun getUserById(userId: String): Flow<UserModel?> = service.getItemById(id = userId).map { it?.toUserModel() }

    override fun updateUser(userId: String, user: UserModel) = service.updateItem(id = userId, item = user.toUserDto())

    override fun deleteUser(userId: String) = service.deleteItem(id = userId)
}