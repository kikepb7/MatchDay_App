package com.example.domain.feature.user.model

data class UserModel(
    val id: String? = "",
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val phoneNumber: String = "",
    val imageUrl: String? = "",
    val playerId: String? = null,
    val clubId: String? = "",
    val role: String = "player",
    val position: String = "",
    val number: Int? = null,
    val createdAt: Long? = null
)