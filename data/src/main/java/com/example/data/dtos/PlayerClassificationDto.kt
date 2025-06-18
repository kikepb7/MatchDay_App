package com.example.data.dtos

data class PlayerClassificationDto(
    val playerId: String = "",
    val name: String = "",
    val goals: Int = 0,
    val assists: Int = 0,
    val minutes: Int = 0,
    val yellowCards: Int = 0,
    val redCards: Int = 0,
    val goalsConceded: Int = 0,
    val gamesPlayed: Int = 0,
    val mvps: Int = 0
)