package mosis.streetsandtotems.feature_map.domain.use_case

import com.google.firebase.firestore.GeoPoint
import mosis.streetsandtotems.feature_map.domain.model.ResourceData
import mosis.streetsandtotems.feature_map.domain.model.ResourceType
import mosis.streetsandtotems.feature_map.domain.repository.MapViewModelRepository
import mosis.streetsandtotems.feature_map.presentation.components.search_results.ResourceSearchResultItem

class SearchResources(private val repository: MapViewModelRepository) {
    operator fun invoke(
        type: ResourceType,
        radius: Double,
        userLocation: GeoPoint,
        onSearchCompleted: (List<ResourceSearchResultItem>) -> Unit,
        onSearchFailed: () -> Unit,
        onResultItemClick: (ResourceData) -> Unit
    ) = repository.searchResourceInRadius(
        type, radius, userLocation, onSearchCompleted, onSearchFailed, onResultItemClick
    )
}