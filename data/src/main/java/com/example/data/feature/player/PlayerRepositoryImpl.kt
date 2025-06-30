package com.example.data.feature.player

import com.example.data.feature.player.dto.PlayerDto
import com.example.data.feature.player.mapper.toPlayerDto
import com.example.data.feature.player.mapper.toPlayerModel
import com.example.domain.feature.player.model.PlayerModel
import com.example.domain.feature.player.repository.PlayerRepository
import com.example.firebase.firestore.FirebaseFirestoreGenericService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlayerRepositoryImpl(
    private val firestore: FirebaseFirestore
): PlayerRepository {

    companion object {
        const val PLAYER_PATH = "players"
    }

    private val service = FirebaseFirestoreGenericService(
        firestore = firestore,
        collectionPath = PLAYER_PATH,
        clazz = PlayerDto::class.java
    )

    override suspend fun createPlayer(player: PlayerModel): String = service.createItem(item = player.toPlayerDto())

    override fun getPlayerById(playerId: String): Flow<PlayerModel?> = service.getItemById(id = playerId).map { it?.toPlayerModel() }

    override fun getAllPlayers(): Flow<List<PlayerModel>> = service.getAllItems().map { list -> list.map { it.toPlayerModel() } }

    override suspend fun updatePlayer(playerId: String, player: PlayerModel) = service.updateItem(id = playerId, item = player.toPlayerDto())

    override suspend fun deletePlayer(playerId: String) = service.deleteItem(id = playerId)
}