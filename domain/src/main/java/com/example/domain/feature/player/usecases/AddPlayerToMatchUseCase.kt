package com.example.domain.feature.player.usecases

import com.example.domain.feature.match.repository.MatchRepository
import com.example.domain.feature.player.repository.PlayerRepository
import kotlinx.coroutines.flow.firstOrNull

class AddPlayerToMatchUseCase(
    private val matchRepository: MatchRepository,
    private val playerRepository: PlayerRepository
) {

    suspend operator fun invoke(matchId: String, playerId: String, team: String): Result<Unit> {
        val match = matchRepository.getMatchById(matchId = matchId).firstOrNull() ?: return Result.failure(
            Exception("Partido no encontrado"))

        val updatedMatch = when (team.lowercase()) {
            "white" -> match.copy(whiteTeam = match.whiteTeam + playerId)
            "blue" -> match.copy(blueTeam = match.blueTeam + playerId)
            else -> return Result.failure(Exception("Equipo no válido"))
        }

        matchRepository.updateMatch(matchId = matchId, match = updatedMatch)

        return Result.success(Unit)
    }
}