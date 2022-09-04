package mosis.streetsandtotems.feature_map.presentation

import androidx.compose.runtime.MutableState
import com.google.firebase.firestore.GeoPoint
import mosis.streetsandtotems.feature_map.domain.model.*
import ovh.plrapps.mapcompose.ui.state.MapState

data class MapScreenState(
    val mapState: MutableState<MapState>,
    val customPinDialog: SelectedCustomPinDialog,
    val playerDialogOpen: Boolean,
    val filterDialogOpen: Boolean,
    val followMe: Boolean,
    val detectScroll: Boolean,
    val filterResources: Boolean,
    val filterPlayers: Boolean,
    val filterTotems: Boolean,

    val resourcesHashMap: Map<String, ResourceData>,
    val playersHashMap: Map<String, UserInGameData>,
    val totemsHashMap: Map<String, TotemData>,
    val customPinsHashMap: Map<String, CustomPinData>,

    val home: HomeData,

    val playerLocation: GeoPoint,

    val selectedPlayer: UserInGameData
)


data class SelectedCustomPinDialog(
    val dialogOpen: Boolean,
    val id: String?,
    val placedBy: String?,
    val l: GeoPoint,
    val text: MutableState<String>,
)