package com.example.domain.feature.user.usecases

import com.example.domain.feature.club.model.ClubModel
import com.example.domain.feature.club.repository.ClubRepository
import com.example.domain.feature.user.model.UserModel
import com.example.domain.feature.user.repository.UserRepository
import java.util.Calendar
import java.util.UUID

class RegisterAdminUserCase(
    private val userRepository: UserRepository,
    private val clubRepository: ClubRepository
) {
    suspend operator fun invoke(user: UserModel, club: ClubModel): Result<Pair<UserModel, ClubModel>> {
        return try {
            if (user.name.isBlank() || user.lastName.isBlank() || user.email.isBlank() || user.password.isBlank() || club.name.isBlank()) {
                return Result.failure(IllegalArgumentException("Campos obligatorios vacíos"))
            }

            val userId = user.id.toString()
            val clubId = UUID.randomUUID().toString()
            val inviteCode = generateInviteCode(club.name)

            val userToCreate = user.copy(rol = "admin", clubId = clubId)

            val initialClub = club.copy(id = clubId, createdAt = Calendar.getInstance().timeInMillis, inviteCode = inviteCode)

            clubRepository.createClub(initialClub)
            userRepository.createUser(userToCreate)

            val updatedClub = initialClub.copy(
                adminUserId = listOf(userId),
                members = listOf(userId)
            )

            clubRepository.updateClub(clubId, updatedClub)

            Result.success(user to updatedClub)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun generateInviteCode(clubName: String): String {
        return clubName.take(4).uppercase() + (1000..9999).random()
    }
}