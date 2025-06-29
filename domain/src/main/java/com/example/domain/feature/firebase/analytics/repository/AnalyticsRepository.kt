package com.example.domain.feature.firebase.analytics.repository

interface AnalyticsRepository {
    fun logEvent(name: String, params: Map<String, String>? =  null)
}