package com.example.data.feature.statistics

import com.example.data.feature.statistics.dto.StatisticDto
import com.example.data.feature.statistics.mapper.toStatisticDto
import com.example.data.feature.statistics.mapper.toStatisticModel
import com.example.domain.feature.statistics.model.StatisticModel
import com.example.domain.feature.statistics.repository.StatisticsRepository
import com.example.firebase.firestore.FirebaseFirestoreGenericService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class StatisticsRepositoryImpl(
    private val firestore: FirebaseFirestore
): StatisticsRepository {

    companion object {
        const val STATISTIC_PATH = "statistics"
        const val MATCHES_PATH = "matches"
        fun matchStatisticsPath(matchId: String) = "$MATCHES_PATH/$matchId/$STATISTIC_PATH"
        fun statisticForMatchPath(matchId: String, statId: String) = "$MATCHES_PATH/$matchId/$STATISTIC_PATH/$statId"
    }

    private val service = FirebaseFirestoreGenericService(
        firestore = firestore,
        collectionPath = STATISTIC_PATH,
        clazz = StatisticDto::class.java
    )

    override suspend fun createStatistic(statistic: StatisticModel): String =
        service.createItem(statistic.toStatisticDto())

    override suspend fun createStatisticForMatch(matchId: String, stat: StatisticModel): String {
        val path = matchStatisticsPath(matchId)
        return service.createItemInPath(stat.toStatisticDto(), dynamicPath = path)
    }

    override fun getAllStatistics(): Flow<List<StatisticModel>> =
        service.getAllItems().map { list -> list.map { it.toStatisticModel() } }

    override fun getStatisticById(statisticId: String): Flow<StatisticModel?> =
        service.getItemById(statisticId).map { it?.toStatisticModel() }

    override fun getStatisticByMatch(matchId: String): Flow<List<StatisticModel>> =
        service.getItemsFromSubcollection(matchStatisticsPath(matchId))
            .map { list -> list.map { it.toStatisticModel() } }

    override fun getStatisticByPlayer(playerId: String): Flow<List<StatisticModel>> = flow {
        val snapshot = firestore.collection(STATISTIC_PATH)
            .whereEqualTo("playerId", playerId)
            .get()
            .await()
        val stats = snapshot.toObjects(StatisticDto::class.java).map { it.toStatisticModel() }
        emit(stats)
    }

    override suspend fun updateStatistic(statisticId: String, statistic: StatisticModel) =
        service.updateItem(statisticId, statistic.toStatisticDto())

    override suspend fun updateStatisticForMatch(matchId: String, statId: String, statistic: StatisticModel) {
        firestore.document(statisticForMatchPath(matchId, statId))
            .set(statistic.toStatisticDto())
            .await()
    }

    override suspend fun deleteStatistic(statisticId: String) =
        service.deleteItem(statisticId)

    override suspend fun deleteStatisticForMatch(matchId: String, statId: String) {
        firestore.document(statisticForMatchPath(matchId, statId))
            .delete()
            .await()
    }

    override suspend fun deleteAllStatisticsByPlayer(playerId: String) {
        val querySnapshot = firestore.collection(STATISTIC_PATH)
            .whereEqualTo("playerId", playerId)
            .get()
            .await()

        val batch = firestore.batch()
        querySnapshot.documents.forEach { batch.delete(it.reference) }
        batch.commit().await()
    }

    override suspend fun deleteStatisticsByPlayerInMatch(matchId: String, playerId: String) {
        val path = matchStatisticsPath(matchId)
        val querySnapshot = firestore.collection(path)
            .whereEqualTo("playerId", playerId)
            .get()
            .await()

        val batch = firestore.batch()
        querySnapshot.documents.forEach { batch.delete(it.reference) }
        batch.commit().await()
    }

//    override suspend fun createStatistic(statistic: StatisticModel): String = service.createItem(item = statistic.toStatisticDto())
//
//    override fun createStatisticForMatch(matchId: String, stat: StatisticModel): String =
//        service.createItemInPath(item = stat.toStatisticDto(), dynamicPath = matchStatistics(matchId = matchId))
//
//    override fun getAllStatistics(): Flow<List<StatisticModel>> = service.getAllItems().map { list -> list.map { it.toStatisticModel() } }
//
//    override fun getStatisticById(statisticId: String): Flow<StatisticModel?> = service.getItemById(id = statisticId).map { it?.toStatisticModel() }
//
//    override fun getStatisticByMatch(matchId: String): Flow<List<StatisticModel>> =
//        reference.child(matchStatistics(matchId = matchId)).snapshots.map { snapshot ->
//            snapshot.children.mapNotNull { it.getValue(StatisticDto::class.java)?.toStatisticModel() }
//        }
//
//    override fun getStatisticByPlayer(playerId: String): Flow<List<StatisticModel>> =
//        reference.child(STATISTIC_PATH).snapshots.map { snapshot ->
//            snapshot.children.mapNotNull { it.getValue(StatisticDto::class.java)?.toStatisticModel() }
//                .filter { it.playerId == playerId }
//        }
//
//    override fun updateStatistic(statisticId: String, statistic: StatisticModel) = service.updateItem(id = statisticId, item = statistic.toStatisticDto())
//
//    override fun updateStatisticForMatch(matchId: String, statId: String, statistic: StatisticModel) {
//        reference.child(statisticsForMatch(matchId = matchId, statId = statId)).setValue(statistic)
//    }
//
//    override fun deleteStatistic(statisticId: String) = service.deleteItem(id = statisticId)
//
//    override fun deleteStatisticForMatch(matchId: String, statId: String) {
//        reference.child(statisticsForMatch(matchId = matchId, statId = statId)).removeValue()
//    }
//
//    override suspend fun deleteAllStatisticsByPlayer(playerId: String) {
//        val snapshot = reference.child(STATISTIC_PATH).get().await()
//
//        snapshot.children.forEach { child ->
//            val stat = child.getValue(StatisticDto::class.java)
//            if (stat?.playerId == playerId) {
//                child.ref.removeValue()
//            }
//        }
//    }
//
//    override suspend fun deleteStatisticsByPlayerInMatch(matchId: String, playerId: String) {
//        val snapshot = reference.child(matchStatistics(matchId = matchId)).get().await()
//
//        snapshot.children.forEach { child ->
//            val stat = child.getValue(StatisticDto::class.java)
//            if (stat?.playerId == playerId) {
//                child.ref.removeValue()
//            }
//        }
//    }
}