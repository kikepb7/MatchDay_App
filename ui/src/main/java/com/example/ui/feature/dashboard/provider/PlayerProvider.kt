package com.example.ui.feature.dashboard.provider

import com.example.domain.feature.player.model.PlayerModel

val mockPlayers = listOf(
    PlayerModel(
        id = "1",
        name = "Carlos Pérez",
        number = 10,
        position = "delantero",
        photoUrl = null,
        userId = "user_1",
        clubId = "club_1",
        isActive = true
    ),
    PlayerModel(
        id = "2",
        name = "Luis Gómez",
        number = 5,
        position = "defensa",
        photoUrl = null,
        userId = "user_2",
        clubId = "club_1",
        isActive = true
    ),
    PlayerModel(
        id = "3",
        name = "Andrés Torres",
        number = 8,
        position = "centrocampista",
        photoUrl = null,
        userId = "user_3",
        clubId = "club_1",
        isActive = true
    ),
    PlayerModel(
        id = "4",
        name = "David Ruiz",
        number = 1,
        position = "portero",
        photoUrl = null,
        userId = "user_4",
        clubId = "club_1",
        isActive = true
    )
)