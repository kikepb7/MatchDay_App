package com.example.domain.feature.player.usecases

import com.example.domain.feature.player.model.PlayerModel
import com.example.domain.feature.player.repository.PlayerRepository
import com.example.domain.feature.user.model.UserModel
import com.example.domain.feature.user.repository.UserRepository
import java.util.UUID

class RegisterPlayerUseCase(
    private val userRepository: UserRepository,
    private val playerRepository: PlayerRepository
) {

    suspend operator fun invoke(user: UserModel, player: PlayerModel): Result<Pair<UserModel, PlayerModel>> = try {

        val userId = UUID.randomUUID().toString()
        val playerId = UUID.randomUUID().toString()

        val playerToCreate = player.copy(id = playerId, userId = userId)
        playerRepository.createPlayer(player)

        val userToCreate = user.copy(id = userId, role = "player", clubId = player.clubId, playerId = playerId)
        userRepository.createUser(userToCreate)

        Result.success(userToCreate to playerToCreate)
    } catch (e: Exception) {
        Result.failure(e)
    }
}