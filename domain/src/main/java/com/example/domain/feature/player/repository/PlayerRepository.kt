package com.example.domain.feature.player.repository

import com.example.domain.feature.player.model.PlayerModel
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    suspend fun createPlayer(player: PlayerModel): String
    fun getAllPlayers(): Flow<List<PlayerModel>>
    fun getPlayerById(playerId: String): Flow<PlayerModel?>
    fun updatePlayer(playerId: String, player: PlayerModel)
    fun deletePlayer(playerId: String)
}