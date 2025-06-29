package com.example.domain.feature.firebase.notification.repository

interface NotificationRepository {
    suspend fun subscribeToMatchTopic(): Result<Unit>
    suspend fun unsubscribeFromMatchTopic(): Result<Unit>
    suspend fun isSubscribedToMatchTopic(): Boolean
}