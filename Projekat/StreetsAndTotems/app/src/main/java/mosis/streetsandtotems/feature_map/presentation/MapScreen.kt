package mosis.streetsandtotems.feature_map.presentation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.net.Uri
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import mosis.streetsandtotems.core.presentation.components.PlayerDialog
import mosis.streetsandtotems.core.presentation.navigation.navgraphs.MainNavGraph
import mosis.streetsandtotems.feature_map.domain.model.InventoryData
import mosis.streetsandtotems.feature_map.domain.model.UserInventoryData
import mosis.streetsandtotems.feature_map.presentation.components.*
import mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs.CustomAreaHelper
import mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs.CustomFilterDialog
import mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs.CustomMarkerDialog
import mosis.streetsandtotems.feature_map.presentation.components.interactionDialogs.CustomResourceDialog
import mosis.streetsandtotems.feature_map.presentation.util.isTradePossible

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

    PlayerDialog(//fale fje za interakciju sqaud
        isOpen = state.playerDialogOpen,
        onDismissRequest = { mapViewModel.onEvent(MapViewModelEvents.ClosePlayerDialog) },
        isSquadMember = state.selectedPlayer.id == "MYID",////////////////////////////
        tradeEnabled = isTradePossible(
            state.playerLocation,
            state.selectedPlayer.l
        ),
        callsAllowed = true,//state.selectedPlayer.call_privacy_level,
        messagingAllowed = true,//state.selectedPlayer.messaging_privacy_level,
        phoneNumber = state.selectedPlayer.phone_number,
        firstName = state.selectedPlayer.first_name,
        lastName = state.selectedPlayer.last_name,
        userName = state.selectedPlayer.user_name,
        image = if (state.selectedPlayer.image_uri == null) Uri.EMPTY else Uri.parse(state.selectedPlayer.image_uri)
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
//    CustomAreaHelper(
//        row00 = {Text("00")},
////        row01 = {Text("01")},
////        row10 = {Text("10")},
////        row11 = {Text("11")},
////        title = {Text("titlw")},
//        modifier = Modifier.size(200.dp)
//    )

    CustomMarkerDialog(isOpen = true)


}