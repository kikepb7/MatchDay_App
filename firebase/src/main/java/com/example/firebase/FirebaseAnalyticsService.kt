package com.example.firebase

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent

class FirebaseAnalyticsService(
    private val analytics: FirebaseAnalytics
) {

    fun logEvent(name: String, params: Map<String, String>? = null) {
        analytics.logEvent(name) {
            params?.forEach { (key, value) ->
                param(key, value)
            }
        }
    }
}