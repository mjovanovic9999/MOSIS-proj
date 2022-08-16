package mosis.streetsandtotems.feature_map.presentation

import androidx.compose.runtime.MutableState
import ovh.plrapps.mapcompose.ui.state.MapState

data class MapScreenState(
    val mapState: MutableState<MapState>,
    val customPinDialogOpen: Boolean,
    val playerDialogOpen: Boolean
)