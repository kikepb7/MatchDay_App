package com.example.domain.feature.match.usecases

import com.example.domain.feature.match.model.MatchModel
import com.example.domain.feature.match.repository.MatchRepository
import com.example.domain.feature.user.repository.UserRepository
import kotlinx.coroutines.flow.firstOrNull
import java.util.UUID

class CreateMatchUseCase(
    private val matchRepository: MatchRepository,
    private val userRepository: UserRepository
) {

    suspend fun invoke(userId: String, matchNumber: Int, date: Long): Result<Unit> {
        val user = userRepository.getUserById(userId = userId).firstOrNull()

        return if (user != null && user.role == "admin") {
            val match = MatchModel(
                id = UUID.randomUUID().toString(),
                number = matchNumber,
                date = date,
                clubId = user.clubId
            )

            matchRepository.createMatch(match = match)
            Result.success(Unit)
        } else {
            Result.failure(Exception("Solo un administrador puede crear partidos"))
        }
    }
}