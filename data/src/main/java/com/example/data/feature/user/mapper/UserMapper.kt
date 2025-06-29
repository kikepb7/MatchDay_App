package com.example.data.feature.user.mapper

import com.example.data.feature.user.dto.UserDto
import com.example.domain.feature.user.model.UserModel

fun UserDto.toUserModel(): UserModel =
    UserModel(
        id = id,
        name = name,
        lastName = lastName,
        email = email,
        password = password,
        phoneNumber = phoneNumber,
        imageUrl = imageUrl,
        playerId = playerId,
        clubId = clubId,
        role = role,
        position = position,
        number = number,
        createdAt = createdAt
    )

fun UserModel.toUserDto(): UserDto =
    UserDto(
        id = id,
        name = name,
        lastName = lastName,
        email = email,
        password = password,
        phoneNumber = phoneNumber,
        imageUrl = imageUrl,
        playerId = playerId,
        clubId = clubId,
        role = role,
        position = position,
        number = number,
        createdAt = createdAt
    )