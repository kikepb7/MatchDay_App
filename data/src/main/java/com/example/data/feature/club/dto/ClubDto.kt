package com.example.data.feature.club.dto

import java.util.Calendar

data class ClubDto(
    val id: String? = "",
    val name: String = "",
    val description: String = "",
    val createdAt: Long = Calendar.getInstance().timeInMillis,
    val adminUserId: List<String> = emptyList(),
    val inviteCode: String = "",
    val logoUrl: String? = null,
    val memberPlayersIds: List<String> = emptyList(),
    val location: String? = null
)