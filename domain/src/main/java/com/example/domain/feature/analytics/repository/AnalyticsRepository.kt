package com.example.domain.feature.analytics.repository

interface AnalyticsRepository {
    fun logEvent(name: String, params: Map<String, String>? =  null)
}