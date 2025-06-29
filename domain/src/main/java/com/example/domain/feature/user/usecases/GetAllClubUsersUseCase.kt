package com.example.domain.feature.user.usecases

import com.example.domain.feature.club.repository.ClubRepository
import com.example.domain.feature.player.repository.PlayerRepository
import com.example.domain.feature.user.model.ClubMemberModel
import com.example.domain.feature.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetAllClubUsersUseCase(
    private val userRepository: UserRepository,
    private val playerRepository: PlayerRepository,
    private val clubRepository: ClubRepository
) {
    suspend operator fun invoke(clubId: String): Flow<List<ClubMemberModel>> = combine(
        clubRepository.getClubById(clubId = clubId),
        userRepository.getAllUsers(),
        playerRepository.getAllPlayers()
    ) { club, users, players ->
        val membersIds = club?.memberPlayersIds.orEmpty()
        val playersByUserId = players.associateBy { it.userId }

        users
            .filter { it.id in membersIds }
            .map { user ->
                ClubMemberModel(
                    user = user,
                    player = playersByUserId[user.id]
                )
            }
    }
}