package com.example.domain.feature.club.usecases

import com.example.domain.feature.player.model.PlayerModel
import com.example.domain.feature.player.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class GetClubPlayersUseCase(
    private val playerRepository: PlayerRepository
) {

    operator fun invoke(clubId: String): Flow<List<PlayerModel>> = flow {
        val players = playerRepository.getAllPlayers()
            .first()
            .filter { it.isActive && it.clubId == clubId }

        emit(players)
    }
}