package com.example.ui.feature.analytics

import androidx.lifecycle.ViewModel

class AnalyticsViewModel(
    private val registerAnalyticsEventHandler: RegisterAnalyticsEventHandler
) : ViewModel() {

    fun trackRegisterScreenViewed() = registerAnalyticsEventHandler.trackScreenViewed()

    fun trackRegisterAttempt(isAdmin: Boolean) = registerAnalyticsEventHandler.trackRegisterAttempt(isAdmin = isAdmin)

    fun trackRegisterSuccess(userId: String) = registerAnalyticsEventHandler.trackRegisterSuccess(userId = userId)

    fun trackRegisterFailure(error: String) = registerAnalyticsEventHandler.trackRegisterFailure(error = error)
}