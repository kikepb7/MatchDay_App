package com.example.domain.feature.firebase.notification.usecases

import com.example.domain.feature.firebase.notification.repository.NotificationRepository

class IsSubscribedToMatchTopicUseCase(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(): Boolean = notificationRepository.isSubscribedToMatchTopic()
}