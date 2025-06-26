package com.example.ui.feature.login.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Either
import com.example.domain.feature.authentication.usecases.RegisterUserWithGoogleUseCase
import com.example.domain.feature.authentication.usecases.SignUpEmailPasswordUseCase
import com.example.domain.feature.user.usecases.GetUserByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val signUpEmailPasswordUseCase: SignUpEmailPasswordUseCase,
    private val registerUserWithGoogleUseCase: RegisterUserWithGoogleUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<SignUpState>(SignUpState.Idle)
    val state= _state.asStateFlow()

    fun signUpWithEmail(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
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

    fun registerWithGoogle(idToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = SignUpState.Loading

            when (val result = registerUserWithGoogleUseCase(idToken = idToken)) {
                is Either.Success -> {
                    val userId = result.data
                    val user = getUserByIdUseCase(userId).first()
                    _state.value = SignUpState.Success(userId = user?.id.orEmpty(), clubId = user?.clubId.orEmpty())
                }

                is Either.Error -> {
                    _state.value = SignUpState.Error(message = result.error)
                }
            }
        }
    }


//    fun signUpWithPhoneNumber(phoneNumber: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            _state.value = SignUpState.Loading
//
//            when (val result = googleSignInClientProvider.signUpWithPhoneVerification(phoneNumber = phoneNumber, activity = )) {
//                is Either.Success -> _state.value = SignUpState.CodeSent
//                is Either.Error -> _state.value = SignUpState.Error(message = result.error)
//            }
//        }
//    }

//    fun verifyOtp(verificationId: String, otp: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            _state.value = SignUpState.Loading
//
//            when (val result = googleSignInClientProvider.verifyOtpCode(verificationId, otp)) {
//                is Either.Success -> {
//                    val user = getUserByIdUseCase(result.data).first()
//                    _state.value = SignUpState.Success(userId = user?.id.orEmpty(), clubId = user?.clubId.orEmpty())
//                }
//                is Either.Error -> _state.value = SignUpState.Error(result.error)
//            }
//        }
//    }
}

sealed interface SignUpState {
    data object Idle : SignUpState
    data object Loading : SignUpState
    data object CodeSent : SignUpState
    data class Success(val userId: String, val clubId: String) : SignUpState
    data class Error(val message: String) : SignUpState
}