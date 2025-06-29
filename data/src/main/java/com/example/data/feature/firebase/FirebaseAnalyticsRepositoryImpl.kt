package com.example.data.feature.firebase

import com.example.domain.feature.analytics.repository.AnalyticsRepository
import com.example.firebase.FirebaseAnalyticsService

class FirebaseAnalyticsRepositoryImpl(
    private val analyticsManager: FirebaseAnalyticsService
): AnalyticsRepository {

    override fun logEvent(name: String, params: Map<String, String>?) {
        analyticsManager.logEvent(name = name, params = params)
    }
}