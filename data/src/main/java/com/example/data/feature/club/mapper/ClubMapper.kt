package com.example.data.feature.club.mapper

import com.example.data.feature.club.dto.ClubDto
import com.example.domain.feature.club.model.ClubModel

fun ClubDto.toClubModel(): ClubModel =
    ClubModel(
        id = id,
        name = name,
        description = description,
        createdAt = createdAt,
        adminUserId = adminUserId,
        inviteCode = inviteCode,
        logoUrl = logoUrl,
        memberPlayersIds = memberPlayersIds,
        location = location
    )

fun ClubModel.toClubDto(): ClubDto =
    ClubDto(
        id = id,
        name = name,
        description = description,
        createdAt = createdAt,
        adminUserId = adminUserId,
        inviteCode = inviteCode,
        logoUrl = logoUrl,
        memberPlayersIds = memberPlayersIds,
        location = location
    )