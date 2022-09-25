package mosis.streetsandtotems.feature_map.presentation

import androidx.compose.runtime.MutableState
import com.google.firebase.firestore.GeoPoint
import mosis.streetsandtotems.feature_map.domain.model.*
import mosis.streetsandtotems.feature_map.presentation.components.search_results.SearchResultItem
import ovh.plrapps.mapcompose.ui.state.MapState

data class MapScreenState(
    val mapState: MutableState<MapState>,
    val customPinDialog: SelectedCustomPinDialog,
    val filterDialogOpen: Boolean,
    val followMe: Boolean,
    val detectScroll: Boolean,

    val filterResources: Boolean,
    val filterPlayers: Boolean,
    val filterTotems: Boolean,
    val filterCustomPins: Boolean,
    val filterMarket: Boolean,

    val myId: String,
    val mySquadId: String,

    val resourcesHashMap: Map<String, ResourceData>,
    val playersHashMap: Map<String, ProfileData>,
    val totemsHashMap: Map<String, TotemData>,
    val customPinsHashMap: Map<String, CustomPinData>,

    val myLocation: GeoPoint,
    val myInventory: UserInventoryData,

    val playerDialogOpen: Boolean,
    val selectedPlayer: ProfileData,

    val resourceDialogOpen: Boolean,
    val selectedResource: ResourceData,

    val market: MarketData,
    val marketDialogOpen: Boolean,

    val home: HomeData,
    val homeDialogOpen: Boolean,

    val selectedTotem: TotemData,
    val totemDialogOpen: Boolean,

    val riddleDialogOpen: Boolean,
    val riddleData: RiddleData,

    val claimTotemDialog: Boolean,


    val inviteDialogOpen: Boolean,
    val voteDialogOpen: Boolean,
    val interactionUserId: String,
    val interactionUserName: String,

    val searchDialogOpen: Boolean,

    val searchResultDialogOpen: Boolean,
    val searchResultDialogItems: List<SearchResultItem>,

    val farItemDialogOpen: Boolean,
    val farItemIconId: Int?,
    val farIconName: String,
)


data class SelectedCustomPinDialog(
    val dialogOpen: Boolean,
    val id: String?,
    val placedBy: String?,
    val l: GeoPoint,
    val text: MutableState<String>,
    val visible_to: String?,
    val player_name: String?,
)