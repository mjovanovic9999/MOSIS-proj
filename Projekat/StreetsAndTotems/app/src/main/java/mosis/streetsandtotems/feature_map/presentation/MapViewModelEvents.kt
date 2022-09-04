package mosis.streetsandtotems.feature_map.presentation

sealed class MapViewModelEvents {
    object ShowFilterDialog : MapViewModelEvents()
    object CloseFilterDialog : MapViewModelEvents()
    object ShowPlayerDialog : MapViewModelEvents()
    object ClosePlayerDialog : MapViewModelEvents()
    object CloseCustomPinDialog : MapViewModelEvents()
    object AddCustomPin : MapViewModelEvents()
    object UpdateCustomPin : MapViewModelEvents()
    object RemoveCustomPin : MapViewModelEvents()
    object FollowMe : MapViewModelEvents()
    object ResetFilters : MapViewModelEvents()
    object UpdateFilterTotems : MapViewModelEvents()
    object UpdateFilterFriends : MapViewModelEvents()
    object UpdateFilterResource : MapViewModelEvents()
    object AddHome : MapViewModelEvents()
    object RemoveHome : MapViewModelEvents()
}