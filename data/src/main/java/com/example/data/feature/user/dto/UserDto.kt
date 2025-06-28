package com.example.data.feature.user.dto

data class UserDto(
    val id: String? = "",
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val phoneNumber: String = "",
    val imageUrl: String? = "",
    val playerId: String? = null,
    val clubId: String? = null,
    val rol: String = "player",
    val position: String = "",
    val number: Int = 0
)