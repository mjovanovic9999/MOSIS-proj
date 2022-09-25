package mosis.streetsandtotems.feature_map.presentation

import mosis.streetsandtotems.feature_map.domain.model.*

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
    object UpdateFilterResources : MapViewModelEvents()
    object UpdateFilterCustomPins : MapViewModelEvents()
    object UpdateFilterMarket : MapViewModelEvents()
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
    object ShowInviteToSquadDialog : MapViewModelEvents()
    object CloseInviteToSquadDialog : MapViewModelEvents()
    object AcceptSquadInvite : MapViewModelEvents()
    object DeclineSquadInvite : MapViewModelEvents()
    object InviteToSquad : MapViewModelEvents()
    object InitKickFromSquad : MapViewModelEvents()
    object ShowVoteDialog : MapViewModelEvents()
    object CloseVoteDialog : MapViewModelEvents()
    object KickAnswerYesInvite : MapViewModelEvents()
    object KickAnswerNoInvite : MapViewModelEvents()
    object ShowSearchDialog : MapViewModelEvents()
    object HideSearchDialog : MapViewModelEvents()
    data class SearchUsers(val username: String, val radius: Double) : MapViewModelEvents()
    data class SearchResources(val type: ResourceType, val radius: Double) : MapViewModelEvents()
    object HideSearchResultDialog : MapViewModelEvents()
    object CloseFarItemDialogHandler : MapViewModelEvents()
}