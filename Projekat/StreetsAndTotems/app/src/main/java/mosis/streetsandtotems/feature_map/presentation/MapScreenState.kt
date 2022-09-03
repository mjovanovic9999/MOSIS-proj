package mosis.streetsandtotems.feature_map.presentation

import androidx.compose.runtime.MutableState
import com.google.firebase.firestore.GeoPoint
import kotlinx.coroutines.flow.MutableStateFlow
import mosis.streetsandtotems.feature_map.domain.model.*
import ovh.plrapps.mapcompose.ui.state.MapState

data class MapScreenState(
    val mapState: MutableState<MapState>,
    val customPinDialog: SelectedCustomPinDialog,
    val filterDialogOpen: Boolean,
    val followMe: Boolean,
    val detectScroll: Boolean,
    val filterResources: MutableStateFlow<Boolean>,
    val filterPlayers: MutableStateFlow<Boolean>,
    val filterTotems: MutableStateFlow<Boolean>,

    val resourcesHashMap: MutableMap<String, ResourceData>,
    val playersHashMap: MutableMap<String, UserInGameData>,
    val totemsHashMap: MutableMap<String, TotemData>,////////
    val customPinsHashMap: MutableMap<String, CustomPinData>,

    val home: MutableState<HomeData>,///

    val selectedPlayer: MutableState<UserInGameData>,
    val playerDialogOpen: Boolean,
)


data class SelectedCustomPinDialog(
    val dialogOpen: Boolean,
    val id: String?,
    val placedBy: String?,
    val l: GeoPoint,
    val text: MutableState<String>,
)