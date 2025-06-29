package com.example.data.feature.firebase.analytics

import com.example.domain.feature.firebase.analytics.repository.AnalyticsRepository
import com.example.firebase.analytics.FirebaseAnalyticsService

class FirebaseAnalyticsRepositoryImpl(
    private val analyticsManager: FirebaseAnalyticsService
): AnalyticsRepository {

    override fun logEvent(name: String, params: Map<String, String>?) {
        analyticsManager.logEvent(name = name, params = params)
    }
}