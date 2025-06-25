package com.example.domain.feature.statistics.repository

import com.example.domain.feature.statistics.model.StatisticModel
import kotlinx.coroutines.flow.Flow

interface StatisticsRepository {
    suspend fun createStatistic(statistic: StatisticModel): String
    fun createStatisticForMatch(matchId: String, stat: StatisticModel): String
    fun getAllStatistics(): Flow<List<StatisticModel>>
    fun getStatisticById(statisticId: String): Flow<StatisticModel?>
    fun getStatisticByMatch(statisticId: String): Flow<List<StatisticModel>>
    fun getStatisticByPlayer(playerId: String): Flow<List<StatisticModel>>
    fun updateStatistic(statisticId: String, statistic: StatisticModel)
    fun updateStatisticForMatch(matchId: String, statId: String, statistic: StatisticModel)
    fun deleteStatistic(statisticId: String)
    fun deleteStatisticForMatch(matchId: String, statId: String)
    suspend fun deleteAllStatisticsByPlayer(playerId: String)
    suspend fun deleteStatisticsByPlayerInMatch(matchId: String, playerId: String)
}