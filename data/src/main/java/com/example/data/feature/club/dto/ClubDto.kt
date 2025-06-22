package com.example.data.feature.club.dto

import java.util.Calendar

data class ClubDto(
    val id: String? = "",
    val name: String = "",
    val description: String = "",
    val createdAt: Long = Calendar.getInstance().timeInMillis,
    val adminUSerId: List<String> = emptyList(),
    val inviteCode: String = "",
    val logoUrl: String? = null,
    val members: List<String> = emptyList()
)