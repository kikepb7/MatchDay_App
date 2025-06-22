package com.example.data.feature.statistics.mapper

import com.example.data.feature.statistics.dto.StatisticDto
import com.example.domain.feature.statistics.model.StatisticModel

fun StatisticDto.toStatisticModel(): StatisticModel =
    StatisticModel(
        id = id,
        footballMatchId = footballMatchId,
        playerId = playerId,
        goals = goals,
        assists = assists,
        minutesPlayed = minutesPlayed,
        yellowCards = yellowCards,
        redCards = redCards,
        goalsConceded = goalsConceded,
        clubId = clubId
    )

fun StatisticModel.toStatisticDto(): StatisticDto =
    StatisticDto(
        id = id,
        footballMatchId = footballMatchId,
        playerId = playerId,
        goals = goals,
        assists = assists,
        minutesPlayed = minutesPlayed,
        yellowCards = yellowCards,
        redCards = redCards,
        goalsConceded = goalsConceded,
        clubId = clubId
    )