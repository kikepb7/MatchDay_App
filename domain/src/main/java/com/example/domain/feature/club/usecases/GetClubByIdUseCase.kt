package com.example.domain.feature.club.usecases

import com.example.domain.feature.club.model.ClubModel
import com.example.domain.feature.club.repository.ClubRepository
import kotlinx.coroutines.flow.Flow

class GetClubByIdUseCase(private val clubRepository: ClubRepository) {
    suspend operator fun invoke(clubId: String): Flow<ClubModel?> {
        return clubRepository.getClubById(clubId = clubId)
    }
}