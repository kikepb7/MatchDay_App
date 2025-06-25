package com.example.ui.feature.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Either
import com.example.domain.feature.authentication.usecases.SignUpEmailPasswordUseCase
import com.example.domain.feature.user.usecases.GetUserByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpEmailPasswordUseCase: SignUpEmailPasswordUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<SignUpState>(SignUpState.Idle)
    val state= _state.asStateFlow()

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _state.value = SignUpState.Loading

            when (val result = signUpEmailPasswordUseCase(email = email, password = password)) {
                is Either.Success -> {
                    val userId = result.data

                    val user = getUserByIdUseCase(userId = userId).first()
                    _state.value = SignUpState.Success(userId = user?.id.toString(), clubId = user?.clubId.toString())
                }
                is Either.Error -> {
                    _state.value = SignUpState.Error(result.error.toString())
                }
            }
        }
    }
}

sealed interface SignUpState {
    data object Idle : SignUpState
    data object Loading : SignUpState
    data class Success(val userId: String, val clubId: String) : SignUpState
    data class Error(val message: String) : SignUpState
}