package com.example.data.dtos

import java.util.Calendar

data class UserDto(
    val id: String? = Calendar.getInstance().timeInMillis.hashCode().toString(),
    val name: String = "",
    val lastName: String = "",
    val email: String = "",
    val playerId: String? = null,
    val rol: String = "player"
)