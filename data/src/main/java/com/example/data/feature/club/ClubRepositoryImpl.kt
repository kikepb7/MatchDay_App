package com.example.data.feature.club

import com.example.data.feature.club.dto.ClubDto
import com.example.data.feature.club.mapper.toClubDto
import com.example.data.feature.club.mapper.toClubModel
import com.example.domain.feature.club.model.ClubModel
import com.example.domain.feature.club.repository.ClubRepository
import com.example.firebase.firestore.FirebaseFirestoreGenericService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ClubRepositoryImpl(
    private val firestore: FirebaseFirestore
) : ClubRepository {

    companion object {
        const val CLUB_PATH = "clubs"
    }

    private val service = FirebaseFirestoreGenericService(
        firestore = firestore,
        collectionPath = CLUB_PATH,
        clazz = ClubDto::class.java
    )

    override suspend fun createClub(club: ClubModel): String = service.createItem(item = club.toClubDto(), key = club.id)

    override suspend fun getClubById(clubId: String): Flow<ClubModel?> = service.getItemById(id = clubId).map { it?.toClubModel() }

    override suspend fun getAllClubs(): Flow<List<ClubModel>> = service.getAllItems().map { list -> list.map { it.toClubModel() } }

    override suspend fun updateClub(clubId: String, club: ClubModel) = service.updateItem(id = clubId, item = club.toClubDto())

    override suspend fun deleteClub(clubId: String) = service.deleteItem(id = clubId)
}