package com.example.data.feature.match.mapper

import com.example.data.feature.match.dto.MatchDto
import com.example.domain.feature.match.model.MatchModel

fun MatchDto.toMatchModel(): MatchModel =
    MatchModel(
        id = id,
        number = number,
        date = date,
        whiteTeam = whiteTeam,
        blueTeam = blueTeam,
        whiteTeamGoals = whiteTeamGoals,
        blueTeamGoals = blueTeamGoals,
        mvpId = mvpId,
        clubId = clubId,
        isFinalized = isFinalized
    )

fun MatchModel.toMatchDto(): MatchDto =
    MatchDto(
        id = id,
        number = number,
        date = date,
        whiteTeam = whiteTeam,
        blueTeam = blueTeam,
        whiteTeamGoals = whiteTeamGoals,
        blueTeamGoals = blueTeamGoals,
        mvpId = mvpId,
        clubId = clubId,
        isFinalized = isFinalized
    )