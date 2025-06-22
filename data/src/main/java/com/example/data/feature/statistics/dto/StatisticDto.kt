package com.example.data.feature.statistics.dto

import java.util.UUID

data class StatisticDto(
    val id: String? = UUID.randomUUID().toString(),
    val footballMatchId: String = "",
    val playerId: String = "",
    val goals: Int = 0,
    val assists: Int = 0,
    val minutesPlayed: Int = 0,
    val yellowCards: Int = 0,
    val redCards: Int = 0,
    val goalsConceded: Int = 0,
    val clubId: String? = null
)