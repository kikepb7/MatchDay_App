package com.example.domain.feature.firebase.analytics.usecases

import com.example.domain.feature.firebase.analytics.repository.AnalyticsRepository

class LogAnalyticsEventUseCase(
    private val analyticsRepository: AnalyticsRepository
) {
    operator fun invoke(eventName: String, params: Map<String, String>? = null) {
        analyticsRepository.logEvent(name = eventName, params = params)
    }
}