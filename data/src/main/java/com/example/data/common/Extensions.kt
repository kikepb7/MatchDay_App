package com.example.data.common

import com.example.domain.common.FailureModel

fun FailureDto.toFailureModel(): FailureModel {
    return when (this.code) {
        400 -> FailureModel.UnknownHostError
        401 -> FailureModel.Unauthorized
        else -> FailureModel.GenericError("")
    }
}