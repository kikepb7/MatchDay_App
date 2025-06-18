package com.example.data.dtos

import java.util.Calendar

data class StatisticsDto(
    val id: String? = Calendar.getInstance().timeInMillis.hashCode().toString(),
    val footballMatchId: String = "",
    val playerId: String = "",
    val goals: Int = 0,
    val assists: Int = 0,
    val minutesPlayed: Int = 0,
    val yellowCards: Int = 0,
    val redCards: Int = 0,
    val goalsConceded: Int = 0
)