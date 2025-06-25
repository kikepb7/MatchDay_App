package com.example.ui.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.feature.authentication.usecases.LogoutUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LogoutViewModel(
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<LogoutState>(LogoutState.Idle)
    val state = _state.asStateFlow()

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = LogoutState.Loading

            try {
                logoutUseCase()
                _state.value = LogoutState.Success
            } catch (e: Exception) {
                _state.value = LogoutState.Error("Error cerrando la sesión: ${e.localizedMessage}")
            }
        }
    }
}

sealed interface LogoutState {
    object Idle : LogoutState
    object Loading : LogoutState
    object Success : LogoutState
    data class Error(val message: String) : LogoutState
}