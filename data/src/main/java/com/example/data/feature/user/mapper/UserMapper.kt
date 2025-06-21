package com.example.data.feature.user.mapper

import com.example.data.feature.user.dto.UserDto
import com.example.domain.feature.user.model.UserModel

fun UserDto.toUserModel(): UserModel =
    UserModel(
        id = id,
        name = name,
        lastName = lastName,
        email = email,
        playerId = playerId,
        rol = rol
    )

fun UserModel.toUserDto(): UserDto =
    UserDto(
        id = id,
        name = name,
        lastName = lastName,
        email = email,
        playerId = playerId,
        rol = rol
    )