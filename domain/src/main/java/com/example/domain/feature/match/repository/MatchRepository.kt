package com.example.domain.feature.match.repository

import com.example.domain.feature.match.model.MatchModel
import kotlinx.coroutines.flow.Flow

interface MatchRepository {
    suspend fun createMatch(match: MatchModel): String
    fun getMatchById(matchId: String): Flow<MatchModel?>
    fun getAllMatches(): Flow<List<MatchModel>>
    fun updateMatch(matchId: String, match: MatchModel)
    fun deleteMatch(matchId: String)
}