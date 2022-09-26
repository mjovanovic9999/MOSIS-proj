package mosis.streetsandtotems.feature_map.presentation

import android.net.Uri
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import mosis.streetsandtotems.core.ButtonConstants.ACCEPT
import mosis.streetsandtotems.core.ButtonConstants.DECLINE
import mosis.streetsandtotems.core.ButtonConstants.KICK
import mosis.streetsandtotems.core.ButtonConstants.NO_KICK
import mosis.streetsandtotems.core.MessageConstants.SQUAD_INVITE
import mosis.streetsandtotems.core.MessageConstants.SQUAD_INVITE_QUESTION
import mosis.streetsandtotems.core.MessageConstants.SQUAD_KICK
import mosis.streetsandtotems.core.MessageConstants.SQUAD_KICK_QUESTION1
import mosis.streetsandtotems.core.MessageConstants.SQUAD_KICK_QUESTION2
import mosis.streetsandtotems.core.presentation.components.ConfirmationDialogType
import mosis.streetsandtotems.core.presentation.components.CustomConfirmationDialog
import mosis.streetsandtotems.core.presentation.components.PlayerDialog
import mosis.streetsandtotems.core.presentation.navigation.navgraphs.MainNavGraph
import mosis.streetsandtotems.feature_map.domain.model.InventoryData
import mosis.streetsandtotems.feature_map.domain.model.UserInventoryData
import mosis.streetsandtotems.feature_map.presentation.components.CustomPinDialog
import mosis.streetsandtotems.feature_map.presentation.components.MapComponent
import mosis.streetsandtotems.feature_map.presentation.components.MapFABs
import mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs.*
import mosis.streetsandtotems.feature_map.presentation.util.isInteractionPossible
import mosis.streetsandtotems.feature_map.presentation.util.isPlayerMySquadMember
import mosis.streetsandtotems.feature_map.presentation.util.shouldEnableNumber

@MainNavGraph(start = true)
@Destination
@Composable
fun MapScreen(openDrawer: () -> Unit, mapViewModel: MapViewModel) {
    val state = mapViewModel.mapScreenState.value

    MapComponent(mapState = state.mapState.value)
    MapFABs(openDrawer,
        locateMe = { mapViewModel.onEvent(MapViewModelEvents.FollowMe) },
        followMe = state.followMe,
        showFilterDialog = { mapViewModel.onEvent(MapViewModelEvents.ShowFilterDialog) },
        showSearchDialog = { mapViewModel.onEvent(MapViewModelEvents.ShowSearchDialog) })

    CustomPinDialog(
        isOpen = state.customPinDialog.dialogOpen,
        onDismissRequest = { mapViewModel.onEvent(MapViewModelEvents.CloseCustomPinDialog) },
        dialogText = state.customPinDialog.text,
        isNewPin = state.customPinDialog.id == null,
        isSquadMember = isPlayerMySquadMember(state.mySquadId, state.customPinDialog.visible_to),
        playerName = state.customPinDialog.player_name,
        addCustomPinFB = { mapViewModel.onEvent(MapViewModelEvents.AddCustomPin) },
        updateCustomPin = { mapViewModel.onEvent(MapViewModelEvents.UpdateCustomPin) },
        deleteCustomPin = { mapViewModel.onEvent(MapViewModelEvents.RemoveCustomPin) },
    )

    PlayerDialog(
//fale fje za interakciju trade i to
        isOpen = state.playerDialogOpen,
        onDismissRequest = { mapViewModel.onEvent(MapViewModelEvents.ClosePlayerDialog) },
        isMySquadMember = isPlayerMySquadMember(state.mySquadId, state.selectedPlayer.squad_id),
        tradeEnabled = isInteractionPossible(
            state.myLocation, state.selectedPlayer.l
        ),
        callsAllowed = shouldEnableNumber(
            state.selectedPlayer.call_privacy_level,
            state.mySquadId,
            state.selectedPlayer.squad_id,
            state.selectedPlayer.phone_number
        ),
        messagingAllowed = shouldEnableNumber(
            state.selectedPlayer.messaging_privacy_level,
            state.mySquadId,
            state.selectedPlayer.squad_id,
            state.selectedPlayer.phone_number
        ),
        phoneNumber = state.selectedPlayer.phone_number,
        firstName = state.selectedPlayer.first_name,
        lastName = state.selectedPlayer.last_name,
        userName = state.selectedPlayer.user_name,
        image = if (state.selectedPlayer.image_uri == null) Uri.EMPTY else Uri.parse(state.selectedPlayer.image_uri),
        onInviteToSquad = { mapViewModel.onEvent(MapViewModelEvents.InviteToSquad) },
        onKickFromSquad = { mapViewModel.onEvent(MapViewModelEvents.InitKickFromSquad) },
        shouldEnableSquadInvite = (state.selectedPlayer.squad_id == null || state.selectedPlayer.squad_id == "")
                && isInteractionPossible(state.myLocation, state.selectedPlayer.l),
    )

    CustomFilterDialog(
        isOpen = state.filterDialogOpen,
        onConfirmButtonClick = { mapViewModel.onEvent(MapViewModelEvents.CloseFilterDialog) },
        onDismissRequest = {
            mapViewModel.onEvent(MapViewModelEvents.ResetFilters)
            mapViewModel.onEvent(MapViewModelEvents.CloseFilterDialog)
        },
        updateFilterResources = { mapViewModel.onEvent(MapViewModelEvents.UpdateFilterResources) },
        updateFilterPlayers = { mapViewModel.onEvent(MapViewModelEvents.UpdateFilterFriends) },
        updateFilterTotems = { mapViewModel.onEvent(MapViewModelEvents.UpdateFilterTotems) },
        updateFilterCustomPins = { mapViewModel.onEvent(MapViewModelEvents.UpdateFilterCustomPins) },
        updateFilterMarket = { mapViewModel.onEvent(MapViewModelEvents.UpdateFilterMarket) },
        filterResourceState = mapViewModel.mapScreenState.value.filterResources,
        filterFriendsState = mapViewModel.mapScreenState.value.filterPlayers,
        filterTotemsState = mapViewModel.mapScreenState.value.filterTotems,
        filterCustomPinsState = mapViewModel.mapScreenState.value.filterCustomPins,
        filterMarketState = mapViewModel.mapScreenState.value.filterMarket,
    )

    CustomResourceDialog(
        isOpen = state.resourceDialogOpen,
        onDismissRequest = { mapViewModel.onEvent(MapViewModelEvents.CloseResourceDialog) },
        onTake = { newInventory, newEmptySpacesCount, newResourceItemsLeft ->
            mapViewModel.onEvent(
                MapViewModelEvents.UpdateInventory(
                    UserInventoryData(
                        inventory = newInventory, empty_spaces = newEmptySpacesCount
                    ),
                )
            )
            mapViewModel.onEvent(MapViewModelEvents.UpdateResource(newResourceItemsLeft))
        },
        resourceType = mapViewModel.mapScreenState.value.selectedResource.type,
        itemsLeft = mapViewModel.mapScreenState.value.selectedResource.remaining,
        emptySpaces = state.myInventory.empty_spaces,
        oldInventoryData = state.myInventory.inventory ?: InventoryData(),
    )

    CustomMarketDialog(
        isOpen = state.marketDialogOpen,
        onDismissRequest = { mapViewModel.onEvent(MapViewModelEvents.CloseMarketDialog) },
        items = state.market.items,
        userInventoryData = state.myInventory,
        updateUserInventoryData = { mapViewModel.onEvent(MapViewModelEvents.UpdateInventory(it)) },
        updateMarketItems = { mapViewModel.onEvent(MapViewModelEvents.UpdateMarket(it)) },
    )

    CustomHomeDialog(
        isOpen = state.homeDialogOpen,
        inventoryData = state.home.inventory,
        onDismissRequest = { mapViewModel.onEvent(MapViewModelEvents.CloseHomeDialog) },
        userInventoryData = state.myInventory,
        updateUserInventoryData = { mapViewModel.onEvent(MapViewModelEvents.UpdateInventory(it)) },
        updateHomeItems = { mapViewModel.onEvent(MapViewModelEvents.UpdateHome(it)) },
    )

    CustomTotemDialog(isOpen = state.totemDialogOpen,
        userInventoryData = state.myInventory,
        updateUserInventoryData = { mapViewModel.onEvent(MapViewModelEvents.UpdateInventory(it)) },
        updateTotem = { mapViewModel.onEvent(MapViewModelEvents.UpdateTotem(it)) },
        onDismissRequest = { mapViewModel.onEvent(MapViewModelEvents.CloseTotemDialog) },
        currentTotem = state.selectedTotem,
        onHarvestTotemPoints = { mapViewModel.onEvent(MapViewModelEvents.HarvestTotemPoints) },
        onPickUpTotem = { mapViewModel.onEvent(MapViewModelEvents.ClaimTotem) })

    CustomRiddleDialog(
        isOpen = state.riddleDialogOpen,
        onDismissRequest = { mapViewModel.onEvent(MapViewModelEvents.CloseRiddleDialog) },
        onCorrectAnswerClick = { mapViewModel.onEvent(MapViewModelEvents.CorrectAnswer) },
        onIncorrectAnswerClick = { mapViewModel.onEvent(MapViewModelEvents.IncorrectAnswer) },
        riddleData = state.riddleData,
    )

    CustomClaimTotemDialog(
        isOpen = state.claimTotemDialog,
        onDismissRequest = { mapViewModel.onEvent(MapViewModelEvents.CloseClaimTotemDialog) },
        onClaim = { mapViewModel.onEvent(MapViewModelEvents.ClaimTotem) },
        backpackHasEmptySpace = state.myInventory.empty_spaces != 0,
    )

    CustomConfirmationDialog(
        type = ConfirmationDialogType.Confirm,
        isOpen = state.inviteDialogOpen,
        title = SQUAD_INVITE,
        text = state.interactionUserName + SQUAD_INVITE_QUESTION,
        onConfirmButtonClick = {
            mapViewModel.onEvent(MapViewModelEvents.AcceptSquadInvite)
            mapViewModel.onEvent(MapViewModelEvents.CloseInviteToSquadDialog)
        },
        onDismissButtonClick = {
            mapViewModel.onEvent(MapViewModelEvents.DeclineSquadInvite)
            mapViewModel.onEvent(MapViewModelEvents.CloseInviteToSquadDialog)
        },
        confirmButtonText = ACCEPT,
        dismissButtonText = DECLINE,
    )

    CustomConfirmationDialog(
        type = ConfirmationDialogType.Confirm,
        isOpen = state.voteDialogOpen,
        title = SQUAD_KICK,
        text = SQUAD_KICK_QUESTION1 + state.interactionUserName + SQUAD_KICK_QUESTION2,
        onConfirmButtonClick = {
            mapViewModel.onEvent(MapViewModelEvents.KickAnswerYesInvite)
            mapViewModel.onEvent(MapViewModelEvents.CloseVoteDialog)
        },
        onDismissButtonClick = {
            mapViewModel.onEvent(MapViewModelEvents.KickAnswerNoInvite)
            mapViewModel.onEvent(MapViewModelEvents.CloseVoteDialog)
        },
        confirmButtonText = KICK,
        dismissButtonText = NO_KICK,
    )

    CustomSearchDialog(isOpen = state.searchDialogOpen,
        onDismissRequest = { mapViewModel.onEvent(MapViewModelEvents.HideSearchDialog) },
        onUsersSearch = { username, distance ->
            mapViewModel.onEvent(
                MapViewModelEvents.SearchUsers(
                    username, distance
                )
            )
        },
        onResourcesSearch = { type, distance ->
            mapViewModel.onEvent(
                MapViewModelEvents.SearchResources(
                    type, distance
                )
            )
            mapViewModel.onEvent(MapViewModelEvents.ResetFilters)
        })

    CustomSearchResultDialog(
        isOpen = state.searchResultDialogOpen,
        onDismissRequest = { mapViewModel.onEvent(MapViewModelEvents.HideSearchResultDialog) },
        searchResults = state.searchResultDialogItems
    )

    CustomFarItemDialog(
        farItemDialogOpen = state.farItemDialogOpen,
        onDismissRequest = { mapViewModel.onEvent(MapViewModelEvents.CloseFarItemDialogHandler) },
        itemName = state.farIconName,
        farResourceIconId = state.farItemIconId,
    )

}