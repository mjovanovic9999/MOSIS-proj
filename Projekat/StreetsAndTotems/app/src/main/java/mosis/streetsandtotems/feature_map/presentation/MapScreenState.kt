package mosis.streetsandtotems.feature_map.presentation

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.flow.MutableStateFlow
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
    val filtersFlow: MutableStateFlow<Set<PinTypes>>,
    val pinsFlow: MutableStateFlow<Set<PinDTO>>,
)