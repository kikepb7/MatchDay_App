package com.example.data.feature.match

import com.example.data.feature.match.dto.MatchDto
import com.example.data.feature.match.mapper.toMatchDto
import com.example.data.feature.match.mapper.toMatchModel
import com.example.data.feature.firebase.FirebaseDatabaseGenericService
import com.example.domain.feature.match.model.MatchModel
import com.example.domain.feature.match.repository.MatchRepository
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MatchRepositoryImpl(
    private val reference: DatabaseReference
) : MatchRepository{

    companion object {
        const val MATCH_PATH = "matches"
    }

    private val service = FirebaseDatabaseGenericService(
        reference = reference,
        basePath = MATCH_PATH,
        clazz = MatchDto::class.java
    )

    override suspend fun createMatch(match: MatchModel): String = service.createItem(item = match.toMatchDto())

    override fun getMatchById(matchId: String): Flow<MatchModel?> = service.getItemById(id = matchId).map { it?.toMatchModel() }

    override fun getAllMatches(): Flow<List<MatchModel>> = service.getAllItems().map { list -> list.map { it.toMatchModel() } }

    override fun updateMatch(matchId: String, match: MatchModel) = service.updateItem(id = matchId, item = match.toMatchDto())

    override fun deleteMatch(matchId: String) = service.deleteItem(id = matchId)

}