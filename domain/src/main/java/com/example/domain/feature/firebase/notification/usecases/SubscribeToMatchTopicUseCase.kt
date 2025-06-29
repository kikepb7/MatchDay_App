package com.example.domain.feature.firebase.notification.usecases

import com.example.domain.feature.firebase.notification.repository.NotificationRepository

class SubscribeToMatchTopicUseCase(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(): Result<Unit> = notificationRepository.subscribeToMatchTopic()
}