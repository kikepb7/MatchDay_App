package com.example.ui.feature.analytics

import com.example.domain.feature.firebase.analytics.usecases.LogAnalyticsEventUseCase

class RegisterAnalyticsEventHandler(
    private val logAnalyticsEventUseCase: LogAnalyticsEventUseCase
) {
    fun trackScreenViewed() {
        logAnalyticsEventUseCase("screen_view", mapOf("screen_name" to "RegisterScreen"))
    }

    fun trackRegisterAttempt(isAdmin: Boolean) {
        logAnalyticsEventUseCase("register_attempt", mapOf("role" to if (isAdmin) "admin" else "player"))
    }

    fun trackRegisterSuccess(userId: String) {
        logAnalyticsEventUseCase("register_success", mapOf("user_id" to userId))
    }

    fun trackRegisterFailure(error: String) {
        logAnalyticsEventUseCase("register_failure", mapOf("error" to error))
    }
}