package mosis.streetsandtotems.services.use_case

import com.google.firebase.firestore.GeoPoint
import mosis.streetsandtotems.feature_map.domain.repository.MapServiceRepository

class UpdatePlayerLocation(private val mapServiceRepository: MapServiceRepository) {
    suspend operator fun invoke(latitude: Double, longitude: Double) =
        mapServiceRepository.updateMyLocation(
            GeoPoint(
                latitude,
                longitude
            )
        )
}