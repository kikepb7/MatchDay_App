package com.example.ui.feature.dashboard.provider

import com.example.domain.feature.match.model.MatchModel

val mockMatches = listOf(
    MatchModel(
        id = "match_1",
        number = 1,
        date = System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000,
        whiteTeam = listOf("Carlos", "Luis", "Andrés"),
        blueTeam = listOf("David", "Miguel", "Juan"),
        whiteTeamGoals = 2,
        blueTeamGoals = 1,
        mvpId = "1",
        isFinalized = true,
        clubId = "club_1"
    ),
    MatchModel(
        id = "match_2",
        number = 2,
        date = System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000,
        whiteTeam = listOf("Carlos", "Andrés"),
        blueTeam = listOf("Luis", "David"),
        whiteTeamGoals = 1,
        blueTeamGoals = 3,
        mvpId = "2",
        isFinalized = true,
        clubId = "club_1"
    ),
    MatchModel(
        id = "match_3",
        number = 3,
        date = System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000,
        whiteTeam = emptyList(),
        blueTeam = emptyList(),
        whiteTeamGoals = 0,
        blueTeamGoals = 0,
        mvpId = null,
        isFinalized = false,
        clubId = "club_1"
    )
)