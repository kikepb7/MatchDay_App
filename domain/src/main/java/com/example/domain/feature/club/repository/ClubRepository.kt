package com.example.domain.feature.club.repository

import com.example.domain.feature.club.model.ClubModel
import kotlinx.coroutines.flow.Flow

interface ClubRepository {

    suspend fun createClub(club: ClubModel): String
    suspend fun getClubById(clubId: String): Flow<ClubModel?>
    suspend fun getAllClubs(): Flow<List<ClubModel>>
    suspend fun updateClub(clubId: String, club: ClubModel)
    suspend fun deleteClub(clubId: String)
}