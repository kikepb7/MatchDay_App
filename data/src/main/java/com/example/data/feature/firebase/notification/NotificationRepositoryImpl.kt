package com.example.data.feature.firebase.notification

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.domain.feature.firebase.notification.repository.NotificationRepository
import com.example.firebase.notification.TopicsService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class NotificationRepositoryImpl(
    private val topicsService: TopicsService,
    private val preferences: DataStore<Preferences>
) : NotificationRepository {

    companion object {
        private val MATCH_TOPIC_KEY = booleanPreferencesKey("match_topic_key")
    }

    override suspend fun subscribeToMatchTopic(): Result<Unit> {
        return try {
            topicsService.subscribeToTopic(TopicsService.MATCH_TOPIC)
            preferences.edit { it[MATCH_TOPIC_KEY] = true }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun unsubscribeFromMatchTopic(): Result<Unit> {
        return try {
            topicsService.unsubscribeToTopic(TopicsService.MATCH_TOPIC)
            preferences.edit { it[MATCH_TOPIC_KEY] = false }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun isSubscribedToMatchTopic(): Boolean {
        return preferences.data
            .map { it[MATCH_TOPIC_KEY] ?: false }
            .first()
    }
}