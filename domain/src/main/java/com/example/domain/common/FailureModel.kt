package com.example.domain.common

sealed class FailureModel {
    data object UnknownHostError : FailureModel()
    data object Unauthorized : FailureModel()
    data class GenericError(val message: String) : FailureModel()
}