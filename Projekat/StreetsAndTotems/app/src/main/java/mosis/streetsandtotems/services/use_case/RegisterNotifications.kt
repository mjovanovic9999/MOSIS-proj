package mosis.streetsandtotems.services.use_case

import mosis.streetsandtotems.core.presentation.utils.notification.NotificationProvider
import mosis.streetsandtotems.feature_map.domain.repository.MapServiceRepository

class RegisterNotifications(
    private val mapServiceRepository: MapServiceRepository,
    private val notificationProvider: NotificationProvider
) {
    operator fun invoke() =
        mapServiceRepository.registerNotifications { notificationProvider.notifyNearbyPass() }
}