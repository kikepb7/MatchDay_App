package com.example.data.feature.user.dto

import java.util.UUID

data class UserDto(
    val id: String? = UUID.randomUUID().toString(),
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val playerId: String? = null,
    val clubId: String? = null,
    val rol: String = "player"
)