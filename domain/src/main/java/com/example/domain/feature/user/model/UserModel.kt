package com.example.domain.feature.user.model

data class UserModel(
    val id: String? = "",
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val playerId: String? = null,
    val rol: String = "player"
)