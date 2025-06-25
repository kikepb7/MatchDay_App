package com.example.domain.feature.match.usecases

import com.example.domain.feature.match.model.MatchModel
import com.example.domain.feature.match.repository.MatchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetMatchesUseCase(
    private val matchRepository: MatchRepository
) {
    operator fun invoke(clubId: String): Flow<List<MatchModel>> {
        return matchRepository.getAllMatches()
            .map { matches ->
                matches.filter { it.clubId == clubId }
                    .sortedByDescending { it.date }
            }
    }
}