package mosis.streetsandtotems.feature_map.domain.use_case

import com.google.firebase.firestore.GeoPoint
import mosis.streetsandtotems.feature_map.domain.model.ProfileData
import mosis.streetsandtotems.feature_map.domain.repository.MapViewModelRepository
import mosis.streetsandtotems.feature_map.presentation.components.search_results.PlayerSearchResultItem

class SearchUsers(private val repository: MapViewModelRepository) {
    suspend operator fun invoke(
        username: String,
        radius: Double,
        userLocation: GeoPoint,
        onSearchCompleted: (List<PlayerSearchResultItem>) -> Unit,
        onSearchFailed: () -> Unit,
        onResultItemClick: (ProfileData) -> Unit
    ) = repository.searchUsersInRadius(
        username, radius, userLocation, onSearchCompleted, onSearchFailed, onResultItemClick
    )
}