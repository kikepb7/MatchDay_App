package com.example.data.common

data class FailureDto(val code: Int, val message: String?)

sealed class Failure {
    data class GenericError(val code: Int, val message: String) : Failure()
    data object Unauthorized: Failure()
    data object UnknownHostError: Failure()
}
