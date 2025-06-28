package com.example.domain.feature.club.usecases

import com.example.domain.feature.player.model.PlayerModel
import com.example.domain.feature.player.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetClubPlayersUseCase(
    private val playerRepository: PlayerRepository
) {

    operator fun invoke(clubId: String): Flow<List<PlayerModel>> =
        playerRepository.getAllPlayers().map { players ->
                players.filter { it.isActive && it.clubId == clubId }
            }
}