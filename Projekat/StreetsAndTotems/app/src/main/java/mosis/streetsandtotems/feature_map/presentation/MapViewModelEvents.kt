package mosis.streetsandtotems.feature_map.presentation

import mosis.streetsandtotems.feature_map.domain.model.InventoryData
import mosis.streetsandtotems.feature_map.domain.model.MarketItem
import mosis.streetsandtotems.feature_map.domain.model.TotemData
import mosis.streetsandtotems.feature_map.domain.model.UserInventoryData

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
    object ShowResourceDialog : MapViewModelEvents()
    object CloseResourceDialog : MapViewModelEvents()
    data class UpdateResource(val newCount: Int) : MapViewModelEvents()
    data class UpdateInventory(val newUserInventoryData: UserInventoryData) : MapViewModelEvents()
    object ShowMarketDialog : MapViewModelEvents()
    object CloseMarketDialog : MapViewModelEvents()
    data class UpdateMarket(val newMarket: Map<String, MarketItem>) : MapViewModelEvents()
    object ShowHomeDialog : MapViewModelEvents()
    object CloseHomeDialog : MapViewModelEvents()
    data class UpdateHome(val newHome: InventoryData) : MapViewModelEvents()
    object ShowTotemDialog : MapViewModelEvents()
    object CloseTotemDialog : MapViewModelEvents()
    data class UpdateTotem(val newTotem: TotemData) : MapViewModelEvents()
    object OpenRiddleDialog : MapViewModelEvents()
    object CloseRiddleDialog : MapViewModelEvents()
    object CorrectAnswer : MapViewModelEvents()
    object IncorrectAnswer : MapViewModelEvents()
    object ShowClaimTotemDialog : MapViewModelEvents()
    object CloseClaimTotemDialog : MapViewModelEvents()
    object ClaimTotem : MapViewModelEvents()
    object HarvestTotemPoints : MapViewModelEvents()
}