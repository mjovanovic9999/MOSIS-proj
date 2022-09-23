package mosis.streetsandtotems.feature_map.presentation

import androidx.compose.runtime.Composable

import android.net.Uri
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
import mosis.streetsandtotems.feature_map.presentation.components.*
import mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs.*
import mosis.streetsandtotems.feature_map.presentation.util.isSquadMember
import mosis.streetsandtotems.feature_map.presentation.util.isTradePossible
import mosis.streetsandtotems.feature_map.presentation.util.shouldEnableNumber

@MainNavGraph(start = true)
@Destination
@Composable
fun MapScreen(openDrawer: () -> Unit, mapViewModel: MapViewModel) {
    val state = mapViewModel.mapScreenState.value

    MapComponent(mapState = state.mapState.value)
    MapFABs(
        openDrawer,
        locateMe = { mapViewModel.onEvent(MapViewModelEvents.FollowMe) },
        followMe = state.followMe,
        showFilterDialog = { mapViewModel.onEvent(MapViewModelEvents.ShowFilterDialog) },
    )

    CustomPinDialog(
        isOpen = state.customPinDialog.dialogOpen,
        onDismissRequest = { mapViewModel.onEvent(MapViewModelEvents.CloseCustomPinDialog) },
        dialogText = state.customPinDialog.text,
        isNewPin = state.customPinDialog.id == null,
        isSquadMember = true,////////////////////////////squad_id!=null
        placedBy = state.customPinDialog.placedBy,
        addCustomPinFB = { mapViewModel.onEvent(MapViewModelEvents.AddCustomPin) },
        updateCustomPin = { mapViewModel.onEvent(MapViewModelEvents.UpdateCustomPin) },
        deleteCustomPin = { mapViewModel.onEvent(MapViewModelEvents.RemoveCustomPin) }
    )

    PlayerDialog(
//fale fje za interakciju trade i to
        isOpen = state.playerDialogOpen,
        onDismissRequest = { mapViewModel.onEvent(MapViewModelEvents.ClosePlayerDialog) },
        isSquadMember = isSquadMember(state.mySquadId, state.selectedPlayer.squad_id),
        tradeEnabled = isTradePossible(
            state.playerLocation,
            state.selectedPlayer.l
        ),
        callsAllowed = shouldEnableNumber(
            state.selectedPlayer.call_privacy_level,
            state.mySquadId,
            state.selectedPlayer.squad_id,
            state.selectedPlayer.phone_number
        ),
        messagingAllowed =
        shouldEnableNumber(
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
        isUserInSquad = state.selectedPlayer.squad_id != null && state.selectedPlayer.squad_id != "",
    )

    CustomFilterDialog(
        isOpen = state.filterDialogOpen,
        onConfirmButtonClick = { mapViewModel.onEvent(MapViewModelEvents.CloseFilterDialog) },
        onDismissRequest = {
            mapViewModel.onEvent(MapViewModelEvents.CloseFilterDialog)
            mapViewModel.onEvent(MapViewModelEvents.ResetFilters)
        },
        updateFilterResources = { mapViewModel.onEvent(MapViewModelEvents.UpdateFilterResource) },
        updateFilterPlayers = { mapViewModel.onEvent(MapViewModelEvents.UpdateFilterFriends) },
        updateFilterTotems = { mapViewModel.onEvent(MapViewModelEvents.UpdateFilterTotems) },
        filterResourceState = mapViewModel.mapScreenState.value.filterResources,
        filterFriendsState = mapViewModel.mapScreenState.value.filterPlayers,
        filterTotemsState = mapViewModel.mapScreenState.value.filterTotems,
    )

    CustomResourceDialog(
        isOpen = state.resourceDialogOpen,
        onDismissRequest = { mapViewModel.onEvent(MapViewModelEvents.CloseResourceDialog) },
        onTake = { newInventory, newEmptySpacesCount, newResourceItemsLeft ->
            mapViewModel.onEvent(
                MapViewModelEvents.UpdateInventory(
                    UserInventoryData(
                        inventory = newInventory,
                        empty_spaces = newEmptySpacesCount
                    ),
                )
            )
            mapViewModel.onEvent(MapViewModelEvents.UpdateResource(newResourceItemsLeft))
        },
        resourceType = mapViewModel.mapScreenState.value.selectedResource.type,
        itemsLeft = mapViewModel.mapScreenState.value.selectedResource.remaining,
        emptySpaces = state.playerInventory.empty_spaces,
        oldInventoryData = state.playerInventory.inventory ?: InventoryData(),
    )

    CustomMarketDialog(
        isOpen = state.marketDialogOpen,
        onDismissRequest = { mapViewModel.onEvent(MapViewModelEvents.CloseMarketDialog) },
        items = state.market.items,
        userInventoryData = state.playerInventory,
        updateUserInventoryData = { mapViewModel.onEvent(MapViewModelEvents.UpdateInventory(it)) },
        updateMarketItems = { mapViewModel.onEvent(MapViewModelEvents.UpdateMarket(it)) },
    )

    CustomHomeDialog(
        isOpen = state.homeDialogOpen,
        inventoryData = state.home.inventory,
        onDismissRequest = { mapViewModel.onEvent(MapViewModelEvents.CloseHomeDialog) },
        userInventoryData = state.playerInventory,
        updateUserInventoryData = { mapViewModel.onEvent(MapViewModelEvents.UpdateInventory(it)) },
        updateHomeItems = { mapViewModel.onEvent(MapViewModelEvents.UpdateHome(it)) },
    )

    CustomTotemDialog(
        isOpen = state.totemDialogOpen,
        userInventoryData = state.playerInventory,
        updateUserInventoryData = { mapViewModel.onEvent(MapViewModelEvents.UpdateInventory(it)) },
        updateTotem = { mapViewModel.onEvent(MapViewModelEvents.UpdateTotem(it)) },
        onDismissRequest = { mapViewModel.onEvent(MapViewModelEvents.CloseTotemDialog) },
        currentTotem = state.selectedTotem,
        onHarvestTotemPoints = { mapViewModel.onEvent(MapViewModelEvents.HarvestTotemPoints) },
        onPickUpTotem = { mapViewModel.onEvent(MapViewModelEvents.ClaimTotem) }
    )

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
        backpackHasEmptySpace = state.playerInventory.empty_spaces != 0,
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

}