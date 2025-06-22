package com.example.data.feature.player.dto

import java.util.UUID

data class PlayerDto(
    val id: String? = UUID.randomUUID().toString(),
    val name: String = "",
    val number: Int = 0,
    val position: String = "",
    val photoUrl: String? = null,
    val userId: String? = null,
    val clubId: String? = null,
    val isActive: Boolean = true
)