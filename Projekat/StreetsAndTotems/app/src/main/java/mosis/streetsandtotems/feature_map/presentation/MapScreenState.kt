package mosis.streetsandtotems.feature_map.presentation

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.flow.MutableStateFlow
import mosis.streetsandtotems.feature_map.domain.model.Resource
import mosis.streetsandtotems.feature_map.domain.model.UserInGameData
import ovh.plrapps.mapcompose.ui.state.MapState

data class MapScreenState(
    val mapState: MutableState<MapState>,
    val customPinDialogOpen: Boolean,
    val playerDialogOpen: Boolean,
    val filterDialogOpen: Boolean,
    val followMe: Boolean,
    val detectScroll: Boolean,
    val filterResources: MutableStateFlow<Boolean>,
    val filterPlayers: MutableStateFlow<Boolean>,
    val filterTotems: MutableStateFlow<Boolean>,
    val resourcesHashMap: MutableMap<String, Resource>,
    val playersHashMap: MutableMap<String, UserInGameData>,
    val totemsHashMap: MutableMap<String, Any>,
)