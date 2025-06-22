package com.example.domain.feature.match.model

data class MatchModel(
    val id: String? = "",
    val number: Int = 0,
    val date: Long = 0L,
    val whiteTeam: List<String> = emptyList(),
    val blueTeam: List<String> = emptyList(),
    val whiteTeamGoals: Int = 0,
    val blueTeamGoals: Int = 0,
    val mvpId: String? = null,
    val isFinalized: Boolean = false,
    val clubId: String? = null
)