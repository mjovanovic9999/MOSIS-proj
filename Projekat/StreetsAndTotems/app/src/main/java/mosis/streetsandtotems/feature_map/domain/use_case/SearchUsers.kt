package mosis.streetsandtotems.feature_map.domain.use_case

import com.google.firebase.firestore.GeoPoint
import mosis.streetsandtotems.feature_map.domain.model.UserData
import mosis.streetsandtotems.feature_map.domain.repository.MapViewModelRepository

class SearchUsers(private val repository: MapViewModelRepository) {
    suspend operator fun invoke(
        username: String,
        radius: Double,
        userLocation: GeoPoint,
        onSearchCompleted: (List<UserData>) -> Unit,
        onSearchFailed: () -> Unit
    ) = repository.searchUsersInRadius(
        username, radius, userLocation, onSearchCompleted, onSearchFailed
    )
}