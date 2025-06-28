package com.example.domain.feature.user.model

import com.example.domain.feature.player.model.PlayerModel

data class ClubMemberModel(
    val user: UserModel,
    val player: PlayerModel? = null
)