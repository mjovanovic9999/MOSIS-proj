package mosis.streetsandtotems.feature_map.presentation

import androidx.compose.runtime.MutableState
import mosis.streetsandtotems.feature_map.domain.model.PinDTO
import mosis.streetsandtotems.feature_map.domain.util.PinTypes
import ovh.plrapps.mapcompose.ui.state.MapState

data class MapScreenState(
    val mapState: MutableState<MapState>,
    val customPinDialogOpen: Boolean,
    val playerDialogOpen: Boolean,
    val filterDialogOpen: Boolean,
    val followMe: Boolean,
    val detectScroll: Boolean,
    val filters: Set<PinTypes>,
    val pinsArray: Array<PinDTO>,
)