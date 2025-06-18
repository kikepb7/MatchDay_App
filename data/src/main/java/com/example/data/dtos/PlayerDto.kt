package com.example.data.dtos

import java.util.Calendar

data class PlayerDto(
    val id: String? = Calendar.getInstance().timeInMillis.hashCode().toString(),
    val name: String = "",
    val number: Int = 0,
    val position: String = "",
    val photoUrl: String? = null,
    val userId: String? = null,
    val isActive: Boolean = true
)