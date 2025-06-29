package com.example.domain.feature.analytics.model

data class AnalyticModel(
    val title: String,
    val analyticsString: List<Pair<String, String>> = emptyList(),
    val analyticsDouble: List<Pair<String, Double>> = emptyList(),
    val analyticsLong: List<Pair<String, Long>> = emptyList()
)