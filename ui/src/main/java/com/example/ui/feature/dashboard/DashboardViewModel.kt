package com.example.ui.feature.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.feature.club.model.ClubModel
import com.example.domain.feature.club.usecases.GetClubByIdUseCase
import com.example.domain.feature.match.model.MatchModel
import com.example.domain.feature.match.usecases.CreateMatchUseCase
import com.example.domain.feature.match.usecases.GetMatchesUseCase
import com.example.domain.feature.player.usecases.AddPlayerToMatchUseCase
import com.example.domain.feature.user.model.ClubMemberModel
import com.example.domain.feature.user.model.UserModel
import com.example.domain.feature.user.usecases.GetAllClubUsersUseCase
import com.example.domain.feature.user.usecases.GetUserByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val createMatchUseCase: CreateMatchUseCase,
    private val addPlayerToMatchUseCase: AddPlayerToMatchUseCase,
    private val getAllClubUsersUseCase: GetAllClubUsersUseCase,
    private val getMatchesUseCase: GetMatchesUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getClubByIdUseCase: GetClubByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<DashboardState>(DashboardState.Loading)
    val state = _state.asStateFlow()

    fun loadData(userId: String, clubId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val user = getUserByIdUseCase(userId).first()
                    ?: throw IllegalStateException("Usuario no encontrado")
                val club = getClubByIdUseCase(clubId).first()
                    ?: throw IllegalStateException("Club no encontrado")
                val members = getAllClubUsersUseCase(clubId).first()
                val matches = getMatchesUseCase(clubId).first()

                _state.value = DashboardState.Success(
                    user = user,
                    club = club,
                    members = members,
                    matches = matches
                )
            } catch (e: Exception) {
                _state.value = DashboardState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun createMatch(userId: String, number: Int, date: Long, clubId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = createMatchUseCase.invoke(userId = userId, matchNumber = number, date = date)

            if (result.isSuccess) {
                loadData(userId = userId, clubId = clubId)
                setSuccessMessage(message = "Partido creado correctamente")
            } else {
                _state.value = DashboardState.Error(message = result.exceptionOrNull()?.message ?: "Error al crear el partido")
            }
        }
    }

    fun addPlayerToMatch(matchId: String, playerId: String, team: Team) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = addPlayerToMatchUseCase(matchId = matchId, playerId = playerId, team = team.name.lowercase())

            if (result.isSuccess) {
                setSuccessMessage("Jugador añadido al equipo ${team.name.lowercase()}")
            } else {
                _state.value = DashboardState.Error(result.exceptionOrNull()?.message ?: "Error al añadir jugador")
            }
        }
    }

    fun selectMatch(matchId: String) {
        val currentState = _state.value

        if (currentState is DashboardState.Success) {
            _state.value = currentState.copy(selectedMatchId = matchId)
        }
    }

    fun setSuccessMessage(message: String) {
        val currentState = _state.value

        if (currentState is DashboardState.Success) {
            _state.value = currentState.copy(successMessage = message)
        }
    }

    fun clearMessage() {
        val currentState = _state.value

        if (currentState is DashboardState.Success) {
            _state.value = currentState.copy(successMessage = null)
        }
    }
}

sealed interface DashboardState {
    data object Loading: DashboardState
    data class Success(
        val user: UserModel,
        val club: ClubModel,
        val matches: List<MatchModel> = emptyList(),
        val members: List<ClubMemberModel> = emptyList(),
        val selectedMatchId: String? = null,
        val selectedPlayerId: String? = null,
        val selectedTeam: Team = Team.WHITE,
        val successMessage: String? = null
    ) : DashboardState

    data class Error(val message: String) : DashboardState

    data object Empty : DashboardState
}

enum class Team { WHITE, BLUE}
