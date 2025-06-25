package com.example.data.feature.statistics

import com.example.data.feature.statistics.dto.StatisticDto
import com.example.data.feature.statistics.mapper.toStatisticDto
import com.example.data.feature.statistics.mapper.toStatisticModel
import com.example.data.firebase.FirebaseDatabaseGenericService
import com.example.domain.feature.statistics.model.StatisticModel
import com.example.domain.feature.statistics.repository.StatisticsRepository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class StatisticsRepositoryImpl(
    private val reference: DatabaseReference
): StatisticsRepository {

    companion object {
        const val STATISTIC_PATH = "statistics"
        const val MATCHES_PATH = "matches"

        fun matchStatistics(matchId: String) = "$MATCHES_PATH/$matchId/$STATISTIC_PATH"
        fun statisticsForMatch(matchId: String, statId: String) = "$MATCHES_PATH/$matchId/$STATISTIC_PATH/$statId"
    }

    private val service = FirebaseDatabaseGenericService(
        reference = reference,
        basePath = STATISTIC_PATH,
        clazz = StatisticDto::class.java
    )

    override suspend fun createStatistic(statistic: StatisticModel): String = service.createItem(item = statistic.toStatisticDto())

    override fun createStatisticForMatch(matchId: String, stat: StatisticModel): String =
        service.createItemInPath(item = stat.toStatisticDto(), dynamicPath = matchStatistics(matchId = matchId))

    override fun getAllStatistics(): Flow<List<StatisticModel>> = service.getAllItems().map { list -> list.map { it.toStatisticModel() } }

    override fun getStatisticById(statisticId: String): Flow<StatisticModel?> = service.getItemById(id = statisticId).map { it?.toStatisticModel() }

    override fun getStatisticByMatch(matchId: String): Flow<List<StatisticModel>> =
        reference.child(matchStatistics(matchId = matchId)).snapshots.map { snapshot ->
            snapshot.children.mapNotNull { it.getValue(StatisticDto::class.java)?.toStatisticModel() }
        }

    override fun getStatisticByPlayer(playerId: String): Flow<List<StatisticModel>> =
        reference.child(STATISTIC_PATH).snapshots.map { snapshot ->
            snapshot.children.mapNotNull { it.getValue(StatisticDto::class.java)?.toStatisticModel() }
                .filter { it.playerId == playerId }
        }

    override fun updateStatistic(statisticId: String, statistic: StatisticModel) = service.updateItem(id = statisticId, item = statistic.toStatisticDto())

    override fun updateStatisticForMatch(matchId: String, statId: String, statistic: StatisticModel) {
        reference.child(statisticsForMatch(matchId = matchId, statId = statId)).setValue(statistic)
    }

    override fun deleteStatistic(statisticId: String) = service.deleteItem(id = statisticId)

    override fun deleteStatisticForMatch(matchId: String, statId: String) {
        reference.child(statisticsForMatch(matchId = matchId, statId = statId)).removeValue()
    }

    override suspend fun deleteAllStatisticsByPlayer(playerId: String) {
        val snapshot = reference.child(STATISTIC_PATH).get().await()

        snapshot.children.forEach { child ->
            val stat = child.getValue(StatisticDto::class.java)
            if (stat?.playerId == playerId) {
                child.ref.removeValue()
            }
        }
    }

    override suspend fun deleteStatisticsByPlayerInMatch(matchId: String, playerId: String) {
        val snapshot = reference.child(matchStatistics(matchId = matchId)).get().await()

        snapshot.children.forEach { child ->
            val stat = child.getValue(StatisticDto::class.java)
            if (stat?.playerId == playerId) {
                child.ref.removeValue()
            }
        }
    }
}