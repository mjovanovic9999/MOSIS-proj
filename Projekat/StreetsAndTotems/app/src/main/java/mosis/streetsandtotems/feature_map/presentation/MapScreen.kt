package mosis.streetsandtotems.feature_map.presentation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import mosis.streetsandtotems.core.presentation.navigation.navgraphs.MainNavGraph
import mosis.streetsandtotems.feature_map.presentation.components.CustomPinDialog
import mosis.streetsandtotems.feature_map.presentation.components.MapComponent
import mosis.streetsandtotems.feature_map.presentation.components.MapFABs
import mosis.streetsandtotems.core.presentation.components.PlayerDialog
import mosis.streetsandtotems.feature_map.presentation.components.CustomFilterDialog


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
        onDismissRequest = {
            mapViewModel.closeFilterDialog()
            mapViewModel.dismissFilters()
        },
        filterShowTikis = state.filterShowTikis,
        changeFilterTikis = { mapViewModel.changeFilterTikis() },
        filterShowFriends = state.filterShowFriends,
        changeFilterFriends = { mapViewModel.changeFilterFriends() },
        filterShowResources = state.filterShowResources,
        changeFilterResources = { mapViewModel.changeFilterResources() },
        applyFilters = {
            mapViewModel.closeFilterDialog()

            mapViewModel.applyFilers()
        },
    )

}