package mosis.streetsandtotems.feature_map.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.ramcosta.composedestinations.annotation.Destination
import mosis.streetsandtotems.core.presentation.components.CustomButton
import mosis.streetsandtotems.core.presentation.components.PlayerDialog
import mosis.streetsandtotems.core.presentation.navigation.navgraphs.MainNavGraph
import mosis.streetsandtotems.feature_map.presentation.components.CustomFilterDialog
import mosis.streetsandtotems.feature_map.presentation.components.CustomPinDialog
import mosis.streetsandtotems.feature_map.presentation.components.MapComponent
import mosis.streetsandtotems.feature_map.presentation.components.MapFABs


@OptIn(ExperimentalMaterial3Api::class)
@MainNavGraph(start = true)
@Destination
@Composable
fun MapScreen(drawerState: DrawerState, mapViewModel: MapViewModel) {
    val state = mapViewModel.mapScreenState.value

    MapComponent(mapState = state.mapState.value)
    MapFABs(
        drawerState,
        openCustomPinDialog = { mapViewModel.showPlayerDialog() },
        locateMe = { mapViewModel.followMe() },
        followMe = state.followMe,
        showFilterDialog = { mapViewModel.showFilterDialog() },
    )

    CustomPinDialog(
        isOpen = state.customPinDialogOpen,
        onDismissRequest = { mapViewModel.closeCustomPinDialog() }
    )

    PlayerDialog(
        isOpen = state.playerDialogOpen,
        onDismissRequest = { mapViewModel.closePlayerDialog() })

    CustomFilterDialog(
        isOpen = state.filterDialogOpen,
        onConfirmButtonClick = { mapViewModel.closeFilterDialog() },
        onDismissRequest = {
            mapViewModel.closeFilterDialog()
            mapViewModel.resetFilters()
        },
        updateFilterResources = { mapViewModel.updateFilterResources() },
        updateFilterPlayers = { mapViewModel.updateFilterFriends() },
        updateFilterTotems = { mapViewModel.updateFilterTotems() },
        filterResourceState = mapViewModel.mapScreenState.value.filterResources.collectAsState(),
        filterFriendsState = mapViewModel.mapScreenState.value.filterPlayers.collectAsState(),
        filterTotemsState = mapViewModel.mapScreenState.value.filterTotems.collectAsState(),
    )
    Row {//testtest
        CustomButton(clickHandler = {
            mapViewModel.tempAddDDPIN()
        }, text = "ADD")
    }
}