package com.example.ui.feature.login.register

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Either
import com.example.domain.feature.authentication.usecases.RegisterUserUseCase
import com.example.domain.feature.club.model.ClubModel
import com.example.domain.feature.player.model.PlayerModel
import com.example.domain.feature.player.usecases.RegisterPlayerUseCase
import com.example.domain.feature.user.model.UserModel
import com.example.domain.feature.user.usecases.RegisterAdminUserCase
import com.example.domain.feature.user.usecases.UploadUserImageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUserUseCase: RegisterUserUseCase,
    private val registerAdminUserCase: RegisterAdminUserCase,
    private val registerPlayerUserCase: RegisterPlayerUseCase,
    private val uploadUserImageUseCase: UploadUserImageUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val state = _state.asStateFlow()

    fun registerAdmin(user: UserModel, club: ClubModel) {
        viewModelScope.launch {
            _state.value = RegisterState.Loading

            when (val authResult = registerUserUseCase(user)) {
                is Either.Success -> {
                    val firebaseUserId = authResult.data
                    val userWithId = user.copy(id = firebaseUserId)

                    val result = registerAdminUserCase(userWithId, club)
                    if (result.isSuccess) {
                        val (createdUser, createdClub) = result.getOrNull()!!
                        _state.value = RegisterState.Success(user = createdUser, club = createdClub)
                    } else {
                        _state.value = RegisterState.Error("Error creando club o usuario.")
                    }
                }
                is Either.Error -> {
                    _state.value = RegisterState.Error(authResult.error.toString())
                }
            }
        }
    }

    fun registerPlayer(user: UserModel, player: PlayerModel) {
        viewModelScope.launch {
            _state.value = RegisterState.Loading

            when (val authResult = registerUserUseCase(user)) {
                is Either.Success -> {
                    val result = registerPlayerUserCase(user, player)
                    if (result.isSuccess) {
                        val (createdUser, createdPlayer) = result.getOrNull()!!
                        _state.value = RegisterState.Success(user = createdUser, player = createdPlayer)
                    } else {
                        _state.value = RegisterState.Error("Error creando jugador.")
                    }
                }
                is Either.Error -> {
                    _state.value = RegisterState.Error(authResult.error.toString())
                }
            }
        }
    }

    fun uploadBasicImage(uri: Uri, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val fileName = uri.lastPathSegment ?: "default.jpg"
            val inputStream = context.contentResolver.openInputStream(uri)
            val bytes = inputStream?.readBytes() ?: return@launch
            uploadUserImageUseCase(fileName = fileName, bytes = bytes)
        }
    }
}

sealed interface RegisterState {
    data object Loading : RegisterState

    data class Success(
        val user: UserModel,
        val player: PlayerModel? = null,
        val club: ClubModel? = null,
        val message: String? = null
    ) : RegisterState

    data class Error(val message: String) : RegisterState

    data object Idle : RegisterState
}