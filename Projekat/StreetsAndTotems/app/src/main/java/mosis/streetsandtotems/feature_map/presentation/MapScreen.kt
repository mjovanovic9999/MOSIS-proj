package mosis.streetsandtotems.feature_map.presentation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import mosis.streetsandtotems.core.presentation.navigation.navgraphs.MainNavGraph
import mosis.streetsandtotems.feature_map.presentation.components.CustomPinDialog
import mosis.streetsandtotems.feature_map.presentation.components.MapComponent
import mosis.streetsandtotems.feature_map.presentation.components.MapFABs
import mosis.streetsandtotems.feature_map.presentation.components.PlayerDialog


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
        { mapViewModel.locateMe() })

    CustomPinDialog(
        isOpen = state.customPinDialogOpen,
        onDismissRequest = { mapViewModel.closeCustomPinDialog() }
    )

    PlayerDialog(
        isOpen = state.playerDialogOpen,
        onDismissRequest = { mapViewModel.closePlayerDialog() })
}