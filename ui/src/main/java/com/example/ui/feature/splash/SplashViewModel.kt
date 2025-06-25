package com.example.ui.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.common.Either
import com.example.domain.common.FailureModel
import com.example.domain.feature.splash.usecases.CheckUserSessionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val checkUserSessionUseCase: CheckUserSessionUseCase
) : ViewModel() {

    private val _destination = MutableStateFlow<SplashDestination>(SplashDestination.Loading)
    val destination = _destination.asStateFlow()

    fun checkDestination() {
        viewModelScope.launch {
            when (val result = checkUserSessionUseCase()) {
                is Either.Success -> {
                    _destination.value = if (result.data) SplashDestination.Dashboard else SplashDestination.Login
                }

                is Either.Error -> {
                    _destination.value = SplashDestination.Error(result.error)
                }
            }
        }
    }
}

sealed class SplashDestination {
    data object Loading : SplashDestination()
    data class Error(val error: FailureModel) : SplashDestination()
    data object Login: SplashDestination()
    data object Dashboard : SplashDestination()
}