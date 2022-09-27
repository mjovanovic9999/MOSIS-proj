package mosis.streetsandtotems.services.use_case

import com.google.firebase.firestore.GeoPoint
import mosis.streetsandtotems.feature_map.domain.repository.MapServiceRepository

class UpdateNotificationQueries(private val mapServiceRepository: MapServiceRepository) {
    operator fun invoke(center: GeoPoint) = mapServiceRepository.updateNotificationQueries(center)
}