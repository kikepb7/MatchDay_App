package com.example.domain.feature.user.usecases

import com.example.domain.feature.club.model.ClubModel
import com.example.domain.feature.club.repository.ClubRepository
import com.example.domain.feature.player.model.PlayerModel
import com.example.domain.feature.player.repository.PlayerRepository
import com.example.domain.feature.user.model.UserModel
import com.example.domain.feature.user.repository.UserRepository
import kotlinx.coroutines.flow.first
import java.util.Calendar
import java.util.UUID

class RegisterAdminUserCase(
    private val userRepository: UserRepository,
    private val clubRepository: ClubRepository,
    private val playerRepository: PlayerRepository
) {

    suspend operator fun invoke(
        user: UserModel,
        club: ClubModel,
        player: PlayerModel?
    ): Result<Triple<UserModel, ClubModel, PlayerModel?>> {
        return try {
            if (
                user.name.isBlank() || user.lastName.isBlank() ||
                user.email.isBlank() || user.password.isBlank() ||
                club.name.isBlank()
            ) {
                return Result.failure(IllegalArgumentException("Campos obligatorios vacíos"))
            }

            val userId = user.id ?: UUID.randomUUID().toString()
            val clubId = UUID.randomUUID().toString()
            val inviteCode = generateInviteCode(club.name)

            val userToCreate = user.copy(
                id = userId,
                clubId = clubId,
                number = user.number,
                imageUrl = user.imageUrl,
                playerId = user.playerId,
                position = user.position,
                role = user.role
            )

            val initialClub = club.copy(
                id = clubId,
                createdAt = Calendar.getInstance().timeInMillis,
                inviteCode = inviteCode
            )

            clubRepository.createClub(initialClub)
            userRepository.createUser(userToCreate)

            var createdPlayer: PlayerModel? = null
            val playerId = UUID.randomUUID().toString()

            val playerToCreate = player?.copy(
                id = playerId,
                name = "${user.name} ${user.lastName}",
                userId = userId,
                clubId = clubId,
                position = player.position,
                number = player.number,
                imageUrl = user.imageUrl,
                isActive = player.isActive
            )

            playerToCreate?.let {
                playerRepository.createPlayer(playerToCreate)
            }

            userRepository.updateUser(userId, userToCreate.copy(playerId = playerId))
            createdPlayer = playerToCreate


            val updatedClub = initialClub.copy(
                adminUserId = listOf(userId),
                memberPlayersIds = listOf(userId)
            )

            clubRepository.updateClub(clubId, updatedClub)

            val userFinal = userRepository.getUserById(userId).first()

            if (userFinal != null) {
                Result.success(Triple(userFinal, updatedClub, createdPlayer))
            } else {
                Result.failure(Exception("No se pudo obtener el usuario creado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun generateInviteCode(clubName: String): String {
        return clubName.take(4).uppercase() + (1000..9999).random()
    }
}