package com.example.data.feature.club.mapper

import com.example.data.feature.club.dto.ClubDto
import com.example.domain.feature.club.model.ClubModel

fun ClubDto.toClubModel(): ClubModel =
    ClubModel(
        id = id,
        name = name,
        description = description,
        createdAt = createdAt,
        adminUserId = adminUSerId,
        inviteCode = inviteCode,
        logoUrl = logoUrl,
        members = members
    )

fun ClubModel.toClubDto(): ClubDto =
    ClubDto(
        id = id,
        name = name,
        description = description,
        createdAt = createdAt,
        adminUSerId = adminUserId,
        inviteCode = inviteCode,
        logoUrl = logoUrl,
        members = members
    )