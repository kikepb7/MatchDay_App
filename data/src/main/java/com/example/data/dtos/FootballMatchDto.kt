package com.example.data.dtos

import java.util.Calendar

data class FootballMatchDto(
    val id: String? = Calendar.getInstance().timeInMillis.hashCode().toString(),
    val number: Int = 0,
    val date: Long = 0L,
    val whiteTeam: List<String> = emptyList(),
    val blueTeam: List<String> = emptyList(),
    val whiteTeamGoals: Int = 0,
    val blueTeamGoals: Int = 0,
    val mvpId: String? = null,
    val isFinalized: Boolean = false
)