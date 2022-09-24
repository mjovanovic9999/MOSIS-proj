package mosis.streetsandtotems.feature_map.domain.use_case

import com.google.firebase.firestore.GeoPoint
import mosis.streetsandtotems.feature_map.domain.model.ResourceData
import mosis.streetsandtotems.feature_map.domain.model.ResourceType
import mosis.streetsandtotems.feature_map.domain.repository.MapViewModelRepository

class SearchResources(private val repository: MapViewModelRepository) {
    operator fun invoke(
        type: ResourceType,
        radius: Double,
        userLocation: GeoPoint,
        onSearchCompleted: (List<ResourceData>) -> Unit,
        onSearchFailed: () -> Unit
    ) = repository.searchResourceInRadius(
        type, radius, userLocation, onSearchCompleted, onSearchFailed
    )
}