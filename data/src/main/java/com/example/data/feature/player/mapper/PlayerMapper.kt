package com.example.data.feature.player.mapper

import com.example.data.feature.player.dto.PlayerDto
import com.example.domain.feature.player.model.PlayerModel

fun PlayerDto.toPlayerModel(): PlayerModel =
    PlayerModel(
        id = id,
        name = name,
        number = number,
        position = position,
        photoUrl = photoUrl,
        userId = userId,
        clubId = clubId,
        isActive = isActive
    )

fun PlayerModel.toPlayerDto(): PlayerDto = PlayerDto(
    id = id,
    name = name,
    number = number,
    position = position,
    photoUrl = photoUrl,
    userId = userId,
    clubId = clubId,
    isActive = isActive
)