package com.example.domain.feature.club.model

import java.util.Calendar

data class ClubModel(
    val id: String? = "",
    val name: String = "",
    val description: String = "",
    val createdAt: Long = Calendar.getInstance().timeInMillis,
    val adminUserId: List<String> = emptyList(),
    val inviteCode: String = "",
    val logoUrl: String? = null,
    val members: List<String> = emptyList()
)