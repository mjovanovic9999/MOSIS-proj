package mosis.streetsandtotems.feature_map.presentation

import android.app.Application
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.HandleResponseConstants
import mosis.streetsandtotems.core.MapConstants.INIT_SCROLL_LAT
import mosis.streetsandtotems.core.MapConstants.INIT_SCROLL_LNG
import mosis.streetsandtotems.core.MapConstants.INIT_SCROLL_X
import mosis.streetsandtotems.core.MapConstants.INIT_SCROLL_Y
import mosis.streetsandtotems.core.MapConstants.LEVEL_COUNT
import mosis.streetsandtotems.core.MapConstants.MAX_SCALE
import mosis.streetsandtotems.core.MapConstants.MY_LOCATION_CIRCLE_SIZE
import mosis.streetsandtotems.core.MapConstants.TILE_KEY
import mosis.streetsandtotems.core.MapConstants.TILE_URL_512
import mosis.streetsandtotems.core.MapConstants.TITLE_SIZE
import mosis.streetsandtotems.core.MapConstants.WORKER_COUNT
import mosis.streetsandtotems.core.MessageConstants.CORRECT_ANSWER
import mosis.streetsandtotems.core.MessageConstants.INCORRECT_ANSWER
import mosis.streetsandtotems.core.PinConstants.LAZY_LOADER_ID
import mosis.streetsandtotems.core.PinConstants.MY_PIN
import mosis.streetsandtotems.core.PinConstants.MY_PIN_COLOR
import mosis.streetsandtotems.core.PinConstants.MY_PIN_COLOR_OPACITY
import mosis.streetsandtotems.core.PinConstants.MY_PIN_RADIUS
import mosis.streetsandtotems.core.PinConstants.MY_PIN_Z_INDEX
import mosis.streetsandtotems.core.PointsConversion.TOTEM_BONUS_POINTS_INCORRECT_ANSWER
import mosis.streetsandtotems.core.domain.model.SnackbarSettings
import mosis.streetsandtotems.core.domain.use_case.PreferenceUseCases
import mosis.streetsandtotems.core.presentation.components.SnackbarType
import mosis.streetsandtotems.di.util.SharedFlowWrapper
import mosis.streetsandtotems.feature_map.domain.model.*
import mosis.streetsandtotems.feature_map.domain.repository.MapViewModelRepository
import mosis.streetsandtotems.feature_map.presentation.components.CustomPin
import mosis.streetsandtotems.feature_map.presentation.components.CustomPinImage
import mosis.streetsandtotems.feature_map.presentation.util.*
import mosis.streetsandtotems.services.LocationService
import mosis.streetsandtotems.services.LocationServiceCommonEvents
import mosis.streetsandtotems.services.LocationServiceMapScreenEvents
import ovh.plrapps.mapcompose.api.*
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.layout.Fill
import ovh.plrapps.mapcompose.ui.state.MapState
import ovh.plrapps.mapcompose.ui.state.markers.model.RenderingStrategy
import java.io.FileInputStream
import javax.inject.Inject

@OptIn(ExperimentalClusteringApi::class)
@HiltViewModel
class MapViewModel @Inject constructor(
    private val appContext: Application,
    private val mapViewModelRepository: MapViewModelRepository,
    private val locationServiceMapScreenEvents: SharedFlowWrapper<LocationServiceMapScreenEvents>,
    private val locationServiceCommonEvents: SharedFlowWrapper<LocationServiceCommonEvents>,
    private val showLoader: MutableStateFlow<Boolean>,
    private val preferenceUseCases: PreferenceUseCases,
    private val snackbarSettingsFlow: MutableStateFlow<SnackbarSettings?>,
) : ViewModel() {
    private val _mapScreenState: MutableState<MapScreenState>
    private val _mapState: MapState
    private val filterPlayers = MutableStateFlow(false)
    private val filterTotems = MutableStateFlow(false)
    private val filterResources = MutableStateFlow(false)
    private val filterCustomPins = MutableStateFlow(false)
    private val filterMarket = MutableStateFlow(false)
    private val resourcesHashMap = mutableMapOf<String, ResourceData>()
    private val playersHashMap = mutableStateMapOf<String, ProfileData>()
    private val totemsHashMap = mutableStateMapOf<String, TotemData>()
    private val customPinsHashMap = mutableStateMapOf<String, CustomPinData>()

    val mapScreenState: State<MapScreenState>

    private val mapDimensions = calculateMapDimensions()
    private val circleSize = mutableStateOf(MY_LOCATION_CIRCLE_SIZE.dp / MAX_SCALE)

    private var initialFetchCompleted: Boolean = LocationService.lastKnownLocation != null

    init {
        if (!initialFetchCompleted) viewModelScope.launch {
            showLoader.emit(true)
        }

        _mapScreenState = getInitMapState(LocationService.lastKnownLocation)

        mapScreenState = _mapScreenState
        _mapState = mapScreenState.value.mapState.value


        initMyLocationPinAndRegisterMove()

        registerOnMapStateChangeListener()

        registerFilterPins()

        registerAddCustomPin()

        registerOnPinClick()

        registerOnLocationService()

        registerOnLocationServiceCommonEvents()

        registerGetMyIDs()
    }

    fun onEvent(event: MapViewModelEvents) {
        when (event) {
            MapViewModelEvents.AddCustomPin -> addCustomPinFBHandler()
            MapViewModelEvents.CloseCustomPinDialog -> closeCustomPinDialogHandler()
            MapViewModelEvents.CloseFilterDialog -> closeFilterDialogHandler()
            MapViewModelEvents.FollowMe -> followMeHandler()
            MapViewModelEvents.RemoveCustomPin -> removeCustomPinHandler()
            MapViewModelEvents.ShowFilterDialog -> showFilterDialogHandler()
            MapViewModelEvents.ShowPlayerDialog -> showPlayerDialogHandler()
            MapViewModelEvents.UpdateCustomPin -> updateCustomPinFBHandler()
            MapViewModelEvents.ClosePlayerDialog -> closePlayerDialogHandler()
            MapViewModelEvents.ResetFilters -> resetFiltersHandler()
            MapViewModelEvents.UpdateFilterFriends -> updateFilterFriendsHandler()
            MapViewModelEvents.UpdateFilterResources -> updateFilterResourcesHandler()
            MapViewModelEvents.UpdateFilterTotems -> updateFilterTotemsHandler()
            MapViewModelEvents.UpdateFilterMarket -> updateFilterMarketHandler()
            MapViewModelEvents.UpdateFilterCustomPins -> updateFilterCustomPinsHandler()
            MapViewModelEvents.AddHome -> addHomeHandler()
            MapViewModelEvents.RemoveHome -> removeHomeHandler()
            MapViewModelEvents.ShowResourceDialog -> showResourceDialogHandler()
            MapViewModelEvents.CloseResourceDialog -> closeResourceDialogHandler()
            is MapViewModelEvents.UpdateInventory -> updateInventoryHandler(event.newUserInventoryData)
            is MapViewModelEvents.UpdateResource -> updateResourceHandler(event.newCount)
            MapViewModelEvents.ShowMarketDialog -> showMarketDialogHandler()
            MapViewModelEvents.CloseMarketDialog -> closeMarketDialogHandler()
            is MapViewModelEvents.UpdateMarket -> updateMarketHandler(event.newMarket)
            MapViewModelEvents.CloseHomeDialog -> closeHomeDialogHandler()
            MapViewModelEvents.ShowHomeDialog -> showHomeDialogHandler()
            is MapViewModelEvents.UpdateHome -> updateHomeDialogHandler(event.newHome)
            MapViewModelEvents.CloseTotemDialog -> closeTotemDialogHandler()
            MapViewModelEvents.ShowTotemDialog -> showTotemDialogHandler()
            is MapViewModelEvents.UpdateTotem -> updateTotemDialogHandler(event.newTotem)
            MapViewModelEvents.CloseRiddleDialog -> closeRiddleDialogHandler()
            MapViewModelEvents.OpenRiddleDialog -> showRiddleDialogHandler()
            MapViewModelEvents.CorrectAnswer -> onCorrectAnswerHandler()
            MapViewModelEvents.IncorrectAnswer -> onIncorrectAnswerHandler()
            MapViewModelEvents.CloseClaimTotemDialog -> closeClaimTotemHandler()
            MapViewModelEvents.ShowClaimTotemDialog -> showClaimTotemHandler()
            MapViewModelEvents.ClaimTotem -> claimTotemHandler()
            MapViewModelEvents.HarvestTotemPoints -> harvestPointsAndUpdateTotemHandler()
            MapViewModelEvents.CloseInviteToSquadDialog -> closeInviteToSquadHandler()
            MapViewModelEvents.ShowInviteToSquadDialog -> showInviteToSquadHandler()
            MapViewModelEvents.AcceptSquadInvite -> acceptSquadInviteHandler()
            MapViewModelEvents.DeclineSquadInvite -> declineSquadInviteHandler()
            MapViewModelEvents.InitKickFromSquad -> initKickFromSquadHandler()
            MapViewModelEvents.InviteToSquad -> inviteToSquadHandler()
            MapViewModelEvents.CloseVoteDialog -> closeVoteHandler()
            MapViewModelEvents.ShowVoteDialog -> showVoteHandler()
            MapViewModelEvents.KickAnswerNoInvite -> kickAnswerHandler(false)
            MapViewModelEvents.KickAnswerYesInvite -> kickAnswerHandler(true)
        }
    }

    private fun getInitMapState(lastKnownLocation: GeoPoint?): MutableState<MapScreenState> {
        val tileStreamProvider = TileStreamProvider { row, col, zoomLvl ->
            try {
                val image = Glide.with(appContext).downloadOnly()
                    //   .load("https://api.maptiler.com/maps/streets/256/${zoomLvl}/${col}/${row}.png?key=HqIvIaAAnQt3ibV6COHi")
                    .load("${TILE_URL_512}${zoomLvl}/${col}/${row}.jpg?key=${TILE_KEY}").submit()
                    .get()


                FileInputStream(image)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        return mutableStateOf(MapScreenState(
            mapState = mutableStateOf(MapState(
                levelCount = LEVEL_COUNT,
                fullWidth = mapDimensions,
                fullHeight = mapDimensions,
                workerCount = WORKER_COUNT,
                tileSize = TITLE_SIZE
            ) {
                scroll(INIT_SCROLL_X, INIT_SCROLL_Y)
            }.apply {
                addLayer(tileStreamProvider)
                enableRotation()
                maxScale = MAX_SCALE
                minimumScaleMode = Fill
            }),
            customPinDialog = SelectedCustomPinDialog(
                dialogOpen = false,
                id = null,
                l = GeoPoint(0.0, 0.0),
                text = mutableStateOf(""),
                placedBy = null,
                visible_to = null,
                player_name = null,
            ),
            playerDialogOpen = false,
            filterDialogOpen = false,
            followMe = true,
            detectScroll = true,
            filterPlayers = filterPlayers.value,
            filterTotems = filterTotems.value,
            filterResources = filterResources.value,
            filterCustomPins = filterCustomPins.value,
            filterMarket = filterMarket.value,
            resourcesHashMap = resourcesHashMap,
            playersHashMap = playersHashMap,
            totemsHashMap = totemsHashMap,
            customPinsHashMap = customPinsHashMap,
            selectedPlayer = ProfileData(),
            playerLocation = lastKnownLocation ?: GeoPoint(
                INIT_SCROLL_LAT, INIT_SCROLL_LNG
            ),
            resourceDialogOpen = false,
            selectedResource = ResourceData(),
            playerInventory = UserInventoryData(),
            market = MarketData(),
            marketDialogOpen = false,
            home = HomeData(),
            homeDialogOpen = false,
            myId = "",
            mySquadId = "",
            selectedTotem = TotemData(),
            totemDialogOpen = false,
            riddleDialogOpen = false,
            claimTotemDialog = false,
            riddleData = RiddleData(),
            inviteDialogOpen = false,
            interactionUserName = "",
            interactionUserId = "",
            voteDialogOpen = false,
        )
        )
    }

    private fun registerOnLocationService() {
        viewModelScope.launch {
            locationServiceMapScreenEvents.flow.collect {
                onLocationServiceEvent(it)
            }
        }
    }

    private fun registerOnLocationServiceCommonEvents() {
        viewModelScope.launch {
            locationServiceCommonEvents.flow.collect {
                onLocationServiceCommonEvent(it)
            }
        }
    }

    private fun registerGetMyIDs() {
        viewModelScope.launch {
            _mapScreenState.value = _mapScreenState.value.copy(
                myId = preferenceUseCases.getUserId(),
            )
            preferenceUseCases.getUserSquadIdFlow().collect {
                _mapScreenState.value = _mapScreenState.value.copy(
                    mySquadId = it
                )
            }
        }
    }

    private fun onLocationServiceCommonEvent(event: LocationServiceCommonEvents) {
        when (event) {
            is LocationServiceCommonEvents.UserInventoryChanged -> onUserInventoryChangedHandler(
                event.newInventory
            )
        }
    }

    private suspend fun onLocationServiceEvent(event: LocationServiceMapScreenEvents) {
        when (event) {
            is LocationServiceMapScreenEvents.PlayerMapScreenLocationChanged -> {
                if (!initialFetchCompleted) {
                    showLoader.emit(false)
                    initialFetchCompleted = true
                }
                movePinAtLatLng(MY_PIN, event.newLocation.latitude, event.newLocation.longitude)

                if (mapScreenState.value.followMe) {
                    changeStateDetectScroll(false)

                    centerMeOnMyPin()

                    changeStateDetectScroll(true)
                }

            }
            is LocationServiceMapScreenEvents.PinDataChanged<*> -> handlePinAction(event.pinAction)
            //treba li ovamo market home
            is LocationServiceMapScreenEvents.KickVote -> {
                when (event.kickAction.type) {
                    KickActionType.VoteStarted -> {
                        _mapScreenState.value = mapScreenState.value.copy(
                            interactionUserId = event.kickAction.kickVoteData.user_id ?: "",
                            interactionUserName = playersHashMap[event.kickAction.kickVoteData.user_id]?.user_name
                                ?: ""
                        )
                        showVoteHandler()
                    }
                    KickActionType.VoteEnded -> {
                        closeVoteHandler()
                    }
                }
            }
            is LocationServiceMapScreenEvents.SquadInvite -> {
                _mapScreenState.value = _mapScreenState.value.copy(
                    interactionUserId = event.squadInvite.inviter_id ?: "",
                    interactionUserName = mapScreenState.value.playersHashMap[event.squadInvite.inviter_id]?.user_name
                        ?: ""
                )
                showInviteToSquadHandler()
            }
        }
    }

    private fun onUserInventoryChangedHandler(newInventory: UserInventoryData) {
        _mapScreenState.value = _mapScreenState.value.copy(playerInventory = newInventory)
    }

    private fun <T : Data> handlePinAction(pinAction: PinAction<T>) {
        when (pinAction.action) {
            PinActionType.Added -> addPinHash(pinAction.pinData)
            PinActionType.Modified -> modifyPinHash(pinAction.pinData)
            PinActionType.Removed -> removePinHash(pinAction.pinData)
        }
    }

    private fun addPinHash(dataType: Data) {
        dataType.id?.let {
            var composable: @Composable() (() -> Unit)? = null
            when (dataType) {
                is HomeData -> {
                    _mapScreenState.value = _mapScreenState.value.copy(home = dataType)
                    composable = { CustomPin(resourceId = R.drawable.pin_home) }
                }
                is CustomPinData -> {
                    customPinsHashMap[it] = dataType
                    composable = {
                        if (mapScreenState.value.myId == dataType.placed_by
                            || isSquadMember(
                                mapScreenState.value.mySquadId,
                                customPinsHashMap[it]?.visible_to
                            )
                        ) {
                            Text(dataType.visible_to.toString())
                            CustomPin(resourceId = R.drawable.pin_custom)
                        }
                    }
                }
                is ResourceData -> {
                    resourcesHashMap[it] = dataType
                    composable = {
                        CustomPin(
                            when (dataType.type) {
                                ResourceType.Wood -> R.drawable.pin_wood
                                ResourceType.Brick -> R.drawable.pin_brick
                                ResourceType.Stone -> R.drawable.pin_stone
                                ResourceType.Emerald -> R.drawable.pin_emerald
                                null -> R.drawable.pin_house_discovery_shot
                            }
                        )
                    }
                }
                is TotemData -> {
                    totemsHashMap[it] = dataType
//da bi prikazalo svi totemi
//                    if (dataType.placed_by != null && ((mapScreenState.value.mySquadId != null && dataType.placed_by == mapScreenState.value.mySquadId) || dataType.placed_by == mapScreenState.value.myId)) {
                    composable = { CustomPin(resourceId = R.drawable.pin_tiki) }
//                    }
                }
                is ProfileData -> {
                    if (dataType.is_online == true) {
                        playersHashMap[it] = dataType
                        if (playersHashMap[it] != null)
                            composable = {
                                playersHashMap[it]?.let { it1 ->
                                    CustomPinImage(
                                        mapScreenState.value.mySquadId, it1
                                    )
                                }
                            }
                    }
                }
                is MarketData -> {
                    _mapScreenState.value = _mapScreenState.value.copy(market = dataType)
                    composable = { CustomPin(resourceId = R.drawable.pin_market) }
                }
            }
            if (composable != null) {
                addPin(it, dataType.l, composable)
            }
        }
    }

    private fun addPin(id: String, geoPoint: GeoPoint?, c: @Composable () -> Unit) {
        val offsets = convertGeoPointNullToOffsets(geoPoint, mapDimensions, mapDimensions)
        _mapState.addMarker(
            id,
            offsets[0],
            offsets[1],
            c = c,
            clipShape = null,
            renderingStrategy = RenderingStrategy.LazyLoading(LAZY_LOADER_ID),
            clickable = true,
        )
    }

    private fun modifyPinHash(dataType: Data) {
        dataType.id?.let {
            var oldData: Data? = null
            when (dataType) {
                is HomeData -> {//treba se specificno handluje sliko ko pins
                    oldData = _mapScreenState.value.home
                    _mapScreenState.value = _mapScreenState.value.copy(home = dataType)
                }
                is CustomPinData -> {
                    if (mapScreenState.value.myId == dataType.placed_by || isSquadMember(
                            mapScreenState.value.mySquadId,
                            dataType.visible_to
                        )
                    ) {
                        if (customPinsHashMap.containsKey(it)) {
                            oldData = customPinsHashMap.put(it, dataType)
                        } else addPinHash(dataType)
                    } else removePinHash(dataType)
                }
                is ResourceData -> {
                    oldData = resourcesHashMap.put(it, dataType)
                }
                is TotemData -> {
                    oldData = totemsHashMap.put(it, dataType)
                }
                is ProfileData -> {
                    if (dataType.is_online == true) {
                        if (playersHashMap.containsKey(it)) {
                            oldData = playersHashMap.put(it, dataType)
                        } else addPinHash(dataType)
                    } else removePinHash(dataType)

                }
                is MarketData -> {
                    oldData = _mapScreenState.value.market
                    _mapScreenState.value = _mapScreenState.value.copy(market = dataType)
                }
            }
            if (oldData != null) {
                movePinConditionally(it, dataType.l, oldData.l)
            }
        }
    }

    private fun movePinConditionally(id: String, newGeoPoint: GeoPoint?, oldGeoPoint: GeoPoint?) {
        if (oldGeoPoint != null && newGeoPoint != null && !areGeoPointsEqual(
                newGeoPoint, oldGeoPoint
            )
        ) {
            movePinAtLatLng(id, newGeoPoint.latitude, newGeoPoint.longitude)
        }
    }


    private fun removePinHash(dataType: Data) {
        dataType.id?.let {
            when (dataType) {
                is HomeData -> {
                    _mapScreenState.value = mapScreenState.value.copy(home = HomeData())
                    customPinsHashMap.remove(it)
                }
                is CustomPinData -> {
                    customPinsHashMap.remove(it)
                }
                is ResourceData -> {
                    resourcesHashMap.remove(it)
                }
                is TotemData -> {
                    totemsHashMap.remove(it)
                }
                is ProfileData -> {
                    playersHashMap.remove(it)
                }
                is MarketData -> {
                    _mapScreenState.value = mapScreenState.value.copy(home = HomeData())
                    customPinsHashMap.remove(it)
                }
            }
            removePin(it)
        }
    }


    private fun registerOnMapStateChangeListener() {
        var pinLocation = mapScreenState.value.playerLocation
        var scale = _mapState.scale

        viewModelScope.launch {
            if (mapScreenState.value.followMe) {
                changeStateDetectScroll(false)

                centerMeOnMyPin()

                changeStateDetectScroll(true)
            }
        }

        _mapState.setStateChangeListener {
            if (scale != this.scale) {
                scale = this.scale
                circleSize.value = MY_LOCATION_CIRCLE_SIZE.dp * this.scale / MAX_SCALE
            }

            val markerInfo = _mapState.getMarkerInfo(MY_PIN)
            if (markerInfo != null) {
                pinLocation = convertOffsetsToGeoPoint(
                    markerInfo.x, markerInfo.y, mapDimensions, mapDimensions
                )
            }
            if (mapScreenState.value.followMe && mapScreenState.value.detectScroll && !areGeoPointsEqual(
                    pinLocation, convertOffsetsToGeoPoint(
                        this.centroidX, this.centroidY, mapDimensions, mapDimensions
                    )
                )
            ) {
                _mapScreenState.value = _mapScreenState.value.copy(
                    followMe = false
                )
            }
        }
    }

    private fun registerFilterPins() {
        viewModelScope.launch {
            filterResources.collect { shouldFilter ->
                mapScreenState.value.resourcesHashMap.values.forEach { resource ->
                    if (shouldFilter) {
                        resource.id?.let { removePin(it) }
                    } else {
                        addPinHash(resource)
                    }
                }

            }
        }
        viewModelScope.launch {
            filterPlayers.collect { shouldFilter ->
                mapScreenState.value.playersHashMap.values.forEach { player ->
                    if (shouldFilter) {
                        player.id?.let { removePin(it) }
                    } else {
                        addPinHash(player)
                    }
                }

            }
        }
        viewModelScope.launch {
            filterTotems.collect { shouldFilter ->
                mapScreenState.value.totemsHashMap.values.forEach { totem ->
                    if (shouldFilter) {
                        totem.id?.let { removePin(it) }
                    } else {
                        addPinHash(totem)
                    }
                }
            }
        }
        viewModelScope.launch {
            filterCustomPins.collect { shouldFilter ->
                mapScreenState.value.customPinsHashMap.values.forEach { pin ->
                    if (shouldFilter) {
                        pin.id?.let { removePin(it) }
                    } else {
                        addPinHash(pin)
                    }
                }
            }
        }
        viewModelScope.launch {
            filterMarket.collect { shouldFilter ->
                if (shouldFilter) {
                    mapScreenState.value.market.id?.let { removePin(it) }
                } else {
                    addPinHash(mapScreenState.value.market)
                }

            }
        }
    }

    private fun initMyLocationPinAndRegisterMove() {
        val offset = convertGeoPointNullToOffsets(
            mapScreenState.value.playerLocation,
            mapDimensions,
            mapDimensions,
        )
        _mapState.addLazyLoader(LAZY_LOADER_ID)
        _mapState.addMarker(
            MY_PIN,
            offset[0],
            offset[1],
            c = {
                Canvas(modifier = Modifier.size(circleSize.value), onDraw = {
                    drawCircle(color = Color(MY_PIN_COLOR_OPACITY))
                    drawCircle(color = Color(MY_PIN_COLOR), radius = MY_PIN_RADIUS)
                })
            },
            clipShape = null,
            relativeOffset = Offset(-.5f, -.5f),
            renderingStrategy = RenderingStrategy.LazyLoading("0"),
            clickable = false,
            zIndex = MY_PIN_Z_INDEX,
        )
    }


    private fun changeStateDetectScroll(shouldDetectScroll: Boolean) {
        _mapScreenState.value = _mapScreenState.value.copy(
            detectScroll = shouldDetectScroll
        )
    }


    private fun followMeHandler() {
        viewModelScope.launch {
            centerMeOnMyPin()
            _mapScreenState.value = _mapScreenState.value.copy(followMe = true, detectScroll = true)
        }
    }

    private suspend fun centerMeOnMyPin() {
        val markerInfo = _mapState.getMarkerInfo(MY_PIN)
        if (markerInfo != null) {
            val x = viewModelScope.launch {
                _mapState.scale = MAX_SCALE
                _mapState.centerOnMarker(MY_PIN)
            }
            x.join()
        }
    }


    private fun centerOnPin(pinId: String) {
        val markerInfo = _mapState.getMarkerInfo(pinId)
        if (markerInfo != null) {
            viewModelScope.launch {
                _mapState.centerOnMarker(pinId)
            }
        }
    }

    @OptIn(ExperimentalClusteringApi::class)
    private fun addPinAtOffset(
        pinId: String,
        x: Double,
        y: Double,
        resourceId: Int,
    ) {
        _mapState.addMarker(
            pinId,
            x,
            y,
            c = { CustomPin(resourceId) },
            clipShape = null,
            renderingStrategy = RenderingStrategy.LazyLoading(LAZY_LOADER_ID),
            clickable = true,
        )
    }


    private fun movePinAtLatLng(pinId: String, lat: Double, lng: Double) {
        val cords = convertLatLngToOffsets(lat, lng, mapDimensions, mapDimensions)
        _mapState.moveMarker(
            pinId,
            cords[0],
            cords[1],
        )
    }

    private fun removePin(pinId: String) {
        _mapState.removeMarker(pinId)
    }

    private fun registerOnPinClick() {
        _mapState.onMarkerClick { id, x, y ->
            if (mapScreenState.value.resourcesHashMap.containsKey(id)) {
                _mapScreenState.value = _mapScreenState.value.copy(
                    selectedResource = mapScreenState.value.resourcesHashMap[id]!!
                )
                showResourceDialogHandler()
            } else if (mapScreenState.value.playersHashMap.containsKey(id)) {
                _mapScreenState.value = _mapScreenState.value.copy(
                    selectedPlayer = mapScreenState.value.playersHashMap[id]!!
                )
                showPlayerDialogHandler()

            } else if (mapScreenState.value.totemsHashMap.containsKey(id)) {
                val totem = mapScreenState.value.totemsHashMap[id]!!
                _mapScreenState.value = _mapScreenState.value.copy(
                    selectedTotem = totem
                )
//                if (totem.placed_by != null && ((mapScreenState.value.mySquadId != null && totem.placed_by == mapScreenState.value.mySquadId) || totem.placed_by == mapScreenState.value.myId)) {
                showTotemDialogHandler()
//                } else {//enemy totoem
//                    if (totem.protection_points == null || totem.protection_points < PointsConversion.LOW)
//                showClaimTotemHandler()
//                    else {
//                getRiddle(getProtectionLevelFromPointsNoUnprotected(totem.protection_points!!))//bez !!
//                    }
//                }

            } else if (mapScreenState.value.customPinsHashMap.containsKey(id)) {
                val customPin = mapScreenState.value.customPinsHashMap[id]!!
                showCustomPinDialogHandler(
                    l = customPin.l,
                    id = customPin.id,
                    placedBy = customPin.placed_by,
                    text = customPin.text,
                    visibleTo = customPin.visible_to,
                    player_name = customPin.player_name
                )
            } else if (mapScreenState.value.market.id == id) {
                showMarketDialogHandler()
            } else if (mapScreenState.value.home.id == id) {
                showHomeDialogHandler()
            }
        }
    }

    private fun registerAddCustomPin() {//kako set to handluje?????????? u bazu
        viewModelScope.launch {
            _mapState.onLongPress { x, y ->
                showCustomPinDialogHandler(
                    convertOffsetsToGeoPoint(
                        x, y, mapDimensions, mapDimensions
                    ),
//                    placedBy = mapScreenState.value.myId,
                    visibleTo = mapScreenState.value.mySquadId,
                )
            }
        }
    }

//region handlers

    private fun showCustomPinDialogHandler(
        l: GeoPoint?,
        id: String? = null,
        placedBy: String? = null,
        text: String? = null,
        visibleTo: String? = null,
        player_name: String? = null,
    ) {
        _mapScreenState.value = _mapScreenState.value.copy(
            customPinDialog = _mapScreenState.value.customPinDialog.copy(
                id = id,
                dialogOpen = true,
                l = l!!,
                placedBy = placedBy,
                text = mutableStateOf(text ?: ""),
                visible_to = visibleTo,
                player_name = player_name,
            )
        )
    }

    private fun closeCustomPinDialogHandler() {
        _mapScreenState.value = _mapScreenState.value.copy(
            customPinDialog = SelectedCustomPinDialog(
                dialogOpen = false,
                l = GeoPoint(0.0, 0.0),
                id = null,
                text = mutableStateOf(""),
                placedBy = null,
                visible_to = null,
                player_name = null,
            )
        )
    }

    private fun showPlayerDialogHandler() {
        _mapScreenState.value = _mapScreenState.value.copy(playerDialogOpen = true)
    }

    private fun closePlayerDialogHandler() {
        _mapScreenState.value = _mapScreenState.value.copy(playerDialogOpen = false)
    }

    private fun showResourceDialogHandler() {
        _mapScreenState.value = _mapScreenState.value.copy(resourceDialogOpen = true)
    }

    private fun closeResourceDialogHandler() {
        _mapScreenState.value = _mapScreenState.value.copy(resourceDialogOpen = false)
    }

    private fun showFilterDialogHandler() {
        _mapScreenState.value = _mapScreenState.value.copy(filterDialogOpen = true)
    }

    private fun closeFilterDialogHandler() {
        _mapScreenState.value = _mapScreenState.value.copy(filterDialogOpen = false)
    }

    private fun updateFilterResourcesHandler() {
        viewModelScope.launch {
            filterResources.emit(!filterResources.value)
            _mapScreenState.value =
                _mapScreenState.value.copy(filterResources = !_mapScreenState.value.filterResources)
        }
    }

    private fun updateFilterFriendsHandler() {
        viewModelScope.launch {
            filterPlayers.emit(!filterPlayers.value)
            _mapScreenState.value =
                _mapScreenState.value.copy(filterPlayers = !_mapScreenState.value.filterPlayers)
        }
    }

    private fun updateFilterTotemsHandler() {
        viewModelScope.launch {
            filterTotems.emit(!filterTotems.value)
            _mapScreenState.value =
                _mapScreenState.value.copy(filterTotems = !_mapScreenState.value.filterTotems)
        }
    }

    private fun updateFilterCustomPinsHandler() {
        viewModelScope.launch {
            filterCustomPins.emit(!filterCustomPins.value)
            _mapScreenState.value =
                _mapScreenState.value.copy(filterCustomPins = !_mapScreenState.value.filterCustomPins)
        }
    }

    private fun updateFilterMarketHandler() {
        viewModelScope.launch {
            filterMarket.emit(!filterMarket.value)
            _mapScreenState.value =
                _mapScreenState.value.copy(filterMarket = !_mapScreenState.value.filterMarket)
        }
    }

    private fun resetFiltersHandler() {
        viewModelScope.launch {
            filterResources.emit(false)
            filterTotems.emit(false)
            filterPlayers.emit(false)
            filterCustomPins.emit(false)
            filterMarket.emit(false)
            _mapScreenState.value = _mapScreenState.value.copy(
                filterResources = false,
                filterPlayers = false,
                filterTotems = false,
                filterCustomPins = false,
                filterMarket = false,
            )
        }
    }

    private fun showMarketDialogHandler() {
        _mapScreenState.value = _mapScreenState.value.copy(marketDialogOpen = true)
    }

    private fun closeMarketDialogHandler() {
        _mapScreenState.value = _mapScreenState.value.copy(marketDialogOpen = false)
    }

    private fun showHomeDialogHandler() {
        _mapScreenState.value = _mapScreenState.value.copy(homeDialogOpen = true)
    }

    private fun closeHomeDialogHandler() {
        _mapScreenState.value = _mapScreenState.value.copy(homeDialogOpen = false)
    }

    private fun showTotemDialogHandler() {
        _mapScreenState.value = _mapScreenState.value.copy(totemDialogOpen = true)
    }

    private fun closeTotemDialogHandler() {
        _mapScreenState.value = _mapScreenState.value.copy(totemDialogOpen = false)
    }

    private fun showRiddleDialogHandler() {
        _mapScreenState.value = _mapScreenState.value.copy(riddleDialogOpen = true)
    }

    private fun closeRiddleDialogHandler() {
        _mapScreenState.value = _mapScreenState.value.copy(riddleDialogOpen = false)
    }

    private fun showClaimTotemHandler() {
        _mapScreenState.value = _mapScreenState.value.copy(claimTotemDialog = true)
    }

    private fun closeClaimTotemHandler() {
        _mapScreenState.value = _mapScreenState.value.copy(claimTotemDialog = false)
    }

    private fun showInviteToSquadHandler() {
        _mapScreenState.value = _mapScreenState.value.copy(inviteDialogOpen = true)
    }

    private fun closeInviteToSquadHandler() {
        _mapScreenState.value = _mapScreenState.value.copy(inviteDialogOpen = false)
    }

    private fun showVoteHandler() {
        _mapScreenState.value = _mapScreenState.value.copy(voteDialogOpen = true)
    }

    private fun closeVoteHandler() {
        _mapScreenState.value = _mapScreenState.value.copy(voteDialogOpen = false)
    }

//endregion


//region firebase functions


    private fun addCustomPinFBHandler() {
        viewModelScope.launch {
            mapViewModelRepository.addCustomPin(
                l = mapScreenState.value.customPinDialog.l,
                visible_to = mapScreenState.value.mySquadId,
                placed_by = mapScreenState.value.myId,
                text = mapScreenState.value.customPinDialog.text.value,
            )
        }
    }


    private fun updateCustomPinFBHandler() {
        viewModelScope.launch {
            mapScreenState.value.customPinDialog.id?.let {
                mapViewModelRepository.updateCustomPin(
                    id = it,
                    visible_to = mapScreenState.value.mySquadId,
                    placed_by = mapScreenState.value.myId,
                    text = mapScreenState.value.customPinDialog.text.value,
                )
            }
        }
    }


    private fun removeCustomPinHandler() {
        viewModelScope.launch {
            mapScreenState.value.customPinDialog.id?.let {
                mapViewModelRepository.deleteCustomPin(it)
            }
        }
    }


    private fun addHomeHandler() {
        mapScreenState.value.home.l?.let {
            viewModelScope.launch {
                mapViewModelRepository.addHome(it)
            }
        }
    }

    private fun updateHomeHandler() {}

    private fun removeHomeHandler() {
        viewModelScope.launch {
            mapViewModelRepository.deleteHome()
        }
    }

    private fun updateResourceHandler(newCount: Int) {
        viewModelScope.launch {
            mapScreenState.value.selectedResource.id?.let { id ->
                mapViewModelRepository.updateResource(id, newCount)
            }
        }
    }


    private fun updateInventoryHandler(newUserInventoryData: UserInventoryData) {
        viewModelScope.launch {
            mapViewModelRepository.updateUserInventory(
                newUserInventoryData
            )
        }
    }


    private fun updateMarketHandler(newMarket: Map<String, MarketItem>) {
        viewModelScope.launch {
            mapViewModelRepository.updateMarket(newMarket)
        }
    }

    private fun updateHomeDialogHandler(newHome: InventoryData) {
        viewModelScope.launch {
            mapScreenState.value.home.id?.let {
                mapViewModelRepository.updateHomeInventory(it, newHome)
            }
        }
    }

    private fun updateTotemDialogHandler(newTotem: TotemData) {
        viewModelScope.launch {
            mapScreenState.value.selectedTotem.id?.let {
                mapViewModelRepository.updateTotem(it, newTotem)
            }
        }
    }

    private fun getRiddle(protectionLevel: ProtectionLevel.RiddleProtectionLevel) {
        viewModelScope.launch {
            _mapScreenState.value = _mapScreenState.value.copy(
                riddleData = mapViewModelRepository.getRiddle(protectionLevel) ?: RiddleData(),
            )
            showRiddleDialogHandler()
        }
    }


    private fun onCorrectAnswerHandler() {
        val selectedTotem = mapScreenState.value.selectedTotem.copy(protection_points = 0)
        selectedTotem.id?.let {
            viewModelScope.launch {
                mapViewModelRepository.updateTotem(it, selectedTotem)
                snackbarSettingsFlow.emit(
                    SnackbarSettings(
                        message = CORRECT_ANSWER,
                        duration = SnackbarDuration.Short,
                        snackbarType = SnackbarType.Info,
                        snackbarId = snackbarSettingsFlow.value?.snackbarId?.plus(other = HandleResponseConstants.ID_ADDITION_FACTOR)
                            ?: HandleResponseConstants.DEFAULT_ID
                    )
                )
                showClaimTotemHandler()
            }
        }
    }

    private fun onIncorrectAnswerHandler() {
        val selectedTotem = mapScreenState.value.selectedTotem.copy(
            bonus_points = (mapScreenState.value.selectedTotem.bonus_points
                ?: 0) + TOTEM_BONUS_POINTS_INCORRECT_ANSWER
        )
        selectedTotem.id?.let {
            viewModelScope.launch {
                mapViewModelRepository.updateTotem(it, selectedTotem)
                snackbarSettingsFlow.emit(
                    SnackbarSettings(
                        message = INCORRECT_ANSWER,
                        duration = SnackbarDuration.Short,
                        snackbarType = SnackbarType.Error,
                        snackbarId = snackbarSettingsFlow.value?.snackbarId?.plus(other = HandleResponseConstants.ID_ADDITION_FACTOR)
                            ?: HandleResponseConstants.DEFAULT_ID
                    )
                )
            }
        }
    }

    private fun claimTotemHandler() {
        mapScreenState.value.selectedTotem.id?.let {
            viewModelScope.launch {
                mapViewModelRepository.updateUserInventory(
                    UserInventoryData(
                        empty_spaces = (mapScreenState.value.playerInventory.empty_spaces ?: 1) - 1,
                        inventory = mapScreenState.value.playerInventory.inventory?.copy(
                            totem = (mapScreenState.value.playerInventory.inventory?.totem ?: 0) + 1
                        )
                    )
                )
                harvestPointsAndUpdateTotemHandler(true)
            }
        }
    }

    private fun harvestPointsAndUpdateTotemHandler(shouldDeleteTotem: Boolean = false) {
        val selectedTotem = mapScreenState.value.selectedTotem
        selectedTotem.id?.let {
            viewModelScope.launch {
                mapViewModelRepository.updateSquadLeaderboard(
                    mapScreenState.value.mySquadId,
                    (selectedTotem.bonus_points
                        ?: 0) + calculateTotemTimePoints(selectedTotem.last_visited)
                )
                if (shouldDeleteTotem) {
                    mapViewModelRepository.deleteTotem(it)
                } else {
                    mapViewModelRepository.updateTotem(
                        it, selectedTotem.copy(
                            bonus_points = 0, last_visited = Timestamp.now()
                        )
                    )
                }
            }
        }
    }

    private fun sharePlayerTotemsWithSquad() {
    }

    private fun unShareTotemsWithSquad() {

    }

    private fun declineSquadInviteHandler() {
        _mapScreenState.value.interactionUserId.let {
            if (it != "") viewModelScope.launch {
                mapViewModelRepository.declineInviteToSquad(it)
            }
        }
    }

    private fun acceptSquadInviteHandler() {
        _mapScreenState.value.interactionUserId.let {
            if (it != "") viewModelScope.launch {
                mapViewModelRepository.acceptInviteToSquad(it)
            }
        }
    }


    private fun initKickFromSquadHandler() {
        _mapScreenState.value.selectedPlayer.id?.let {
            viewModelScope.launch {
                mapViewModelRepository.initKickVote(it)
            }
        }
    }

    private fun inviteToSquadHandler() {
        _mapScreenState.value.selectedPlayer.id?.let {
            viewModelScope.launch {
                mapViewModelRepository.initInviteToSquad(it)
            }
        }
    }

    private fun kickAnswerHandler(kick: Boolean) {
        _mapScreenState.value.interactionUserId.let {
            if (it != "") viewModelScope.launch {
                mapViewModelRepository.kickVote(it, if (kick) Vote.Yes else Vote.No)
            }
        }
    }
}

//endregion

