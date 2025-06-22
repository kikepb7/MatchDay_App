package com.example.domain.feature.statistics.model

data class StatisticModel(
    val id: String? = "",
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