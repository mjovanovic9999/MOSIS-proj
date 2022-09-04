package mosis.streetsandtotems.feature_map.presentation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import mosis.streetsandtotems.core.presentation.components.PlayerDialog
import mosis.streetsandtotems.core.presentation.navigation.navgraphs.MainNavGraph
import mosis.streetsandtotems.feature_map.presentation.components.CustomFilterDialog
import mosis.streetsandtotems.feature_map.presentation.components.CustomPinDialog
import mosis.streetsandtotems.feature_map.presentation.components.MapComponent
import mosis.streetsandtotems.feature_map.presentation.components.MapFABs
import mosis.streetsandtotems.feature_map.presentation.util.isTradePossible


@OptIn(ExperimentalMaterial3Api::class)
@MainNavGraph(start = true)
@Destination
@Composable
fun MapScreen(drawerState: DrawerState, mapViewModel: MapViewModel) {
    val state = mapViewModel.mapScreenState.value

    MapComponent(mapState = state.mapState.value)
    MapFABs(
        drawerState,
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
        isOpen = state.playerDialogOpen,
        onDismissRequest = { mapViewModel.onEvent(MapViewModelEvents.ClosePlayerDialog) },
        isSquadMember = state.selectedPlayer.id == "MYID",////////////////////////////
        tradeEnabled = isTradePossible(
            state.playerLocation,
            state.selectedPlayer.l
        ),
        callsAllowed = state.selectedPlayer.calls_allowed,
        messagingAllowed = state.selectedPlayer.messaging_allowed,
        phoneNumber = state.selectedPlayer.phone_number,
        firstName = state.selectedPlayer.first_name,
        lastName = state.selectedPlayer.last_name,
        userName = state.selectedPlayer.user_name,
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
}