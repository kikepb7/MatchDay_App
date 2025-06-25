package com.example.data.feature.player.dto

data class PlayerDto(
    val id: String? = "",
    val name: String = "",
    val number: Int = 0,
    val position: String = "",
    val photoUrl: String? = null,
    val userId: String? = null,
    val clubId: String? = null,
    val isActive: Boolean = true
)