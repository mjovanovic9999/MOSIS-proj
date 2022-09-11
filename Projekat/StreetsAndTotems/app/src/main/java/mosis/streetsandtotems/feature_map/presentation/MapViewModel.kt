package mosis.streetsandtotems.feature_map.presentation

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.google.firebase.firestore.GeoPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import mosis.streetsandtotems.R
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
import mosis.streetsandtotems.core.PinConstants.LAZY_LOADER_ID
import mosis.streetsandtotems.core.PinConstants.MY_PIN
import mosis.streetsandtotems.core.PinConstants.MY_PIN_COLOR
import mosis.streetsandtotems.core.PinConstants.MY_PIN_COLOR_OPACITY
import mosis.streetsandtotems.core.PinConstants.MY_PIN_RADIUS
import mosis.streetsandtotems.core.domain.use_case.PreferenceUseCases
import mosis.streetsandtotems.di.util.SharedFlowWrapper
import mosis.streetsandtotems.feature_map.domain.model.*
import mosis.streetsandtotems.feature_map.domain.repository.MapViewModelRepository
import mosis.streetsandtotems.feature_map.presentation.components.CustomPin
import mosis.streetsandtotems.feature_map.presentation.components.CustomPinImage
import mosis.streetsandtotems.feature_map.presentation.util.*
import mosis.streetsandtotems.services.LocationServiceEvents
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
    private val locationServiceEvents: SharedFlowWrapper<LocationServiceEvents>,
    private val showLoader: MutableStateFlow<Boolean>,
    private val preferenceUseCases: PreferenceUseCases
) : ViewModel() {
    private val _mapScreenState: MutableState<MapScreenState>
    private val _mapState: MapState
    private val filterPlayers = MutableStateFlow(false)
    private val filterTotems = MutableStateFlow(false)
    private val filterResources = MutableStateFlow(false)
    private val resourcesHashMap = mutableMapOf<String, ResourceData>()
    private val playersHashMap = mutableMapOf<String, ProfileData>()
    private val totemsHashMap = mutableMapOf<String, TotemData>()
    private val customPinsHashMap = mutableMapOf<String, CustomPinData>()

    val mapScreenState: State<MapScreenState>

    private val mapDimensions = calculateMapDimensions()
    private val circleSize = mutableStateOf(MY_LOCATION_CIRCLE_SIZE.dp / MAX_SCALE)

    private var initialFetchCompleted = false


    init {
        viewModelScope.launch {
            showLoader.emit(true)
        }

        _mapScreenState = getInitMapState()

        mapScreenState = _mapScreenState
        _mapState = mapScreenState.value.mapState.value


        initMyLocationPinAndRegisterMove()

        registerOnMapStateChangeListener()

        registerFilterPins()

        registerAddCustomPin()

        registerOnPinClick()

        registerOnLocationService()

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
            MapViewModelEvents.UpdateFilterResource -> updateFilterResourcesHandler()
            MapViewModelEvents.UpdateFilterTotems -> updateFilterTotemsHandler()
            MapViewModelEvents.AddHome -> addHomeHandler()
            MapViewModelEvents.RemoveHome -> removeHomeHandler()
            MapViewModelEvents.ShowResourceDialog -> showResourceDialogHandler()
            MapViewModelEvents.CloseResourceDialog -> closeResourceDialogHandler()
            is MapViewModelEvents.UpdateInventory -> updateInventoryHandler(event.newUserInventoryData)
            is MapViewModelEvents.UpdateResource -> updateResourceHandler(
                event.newCount,
            )
        }
    }

    private fun getInitMapState(): MutableState<MapScreenState> {
        val tileStreamProvider = TileStreamProvider { row, col, zoomLvl ->
            try {
                val image = Glide.with(appContext)
                    .downloadOnly()
                    //   .load("https://api.maptiler.com/maps/streets/256/${zoomLvl}/${col}/${row}.png?key=HqIvIaAAnQt3ibV6COHi")
                    .load("${TILE_URL_512}${zoomLvl}/${col}/${row}.jpg?key=${TILE_KEY}")
                    .submit()
                    .get()


                FileInputStream(image)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        return mutableStateOf(MapScreenState(
            mapState = mutableStateOf(
                MapState(
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
                }
            ),
            customPinDialog = SelectedCustomPinDialog(
                dialogOpen = false,
                id = null,
                l = GeoPoint(0.0, 0.0),
                text = mutableStateOf(""),
                placedBy = null
            ),
            playerDialogOpen = false,
            filterDialogOpen = false,
            followMe = true,
            detectScroll = true,
            filterPlayers = filterPlayers.value,
            filterTotems = filterTotems.value,
            filterResources = filterResources.value,
            resourcesHashMap = resourcesHashMap,
            playersHashMap = playersHashMap,
            totemsHashMap = totemsHashMap,
            customPinsHashMap = customPinsHashMap,
            selectedPlayer = ProfileData(),
            home = HomeData(),
            playerLocation = GeoPoint(
                INIT_SCROLL_LAT,
                INIT_SCROLL_LNG
            ),
            resourceDialogOpen = false,
            selectedResource = ResourceData(),
            playerInventory = UserInventoryData(),
        )
        )
    }

    private fun registerOnLocationService() {
        viewModelScope.launch {
            locationServiceEvents.flow.collect {
                onLocationServiceEvent(it)
            }
        }
    }

    private suspend fun onLocationServiceEvent(event: LocationServiceEvents) {
        when (event) {
            is LocationServiceEvents.PlayerLocationChanged -> {
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
            is LocationServiceEvents.PinDataChanged<*> -> handlePinAction(event.pinAction)
            is LocationServiceEvents.UserInventoryChanged -> onUserInventoryChangedHandler(event.newInventory)
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
                    composable = { CustomPin(resourceId = R.drawable.pin_custom) }
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
                    composable = { CustomPin(resourceId = R.drawable.pin_tiki) }
                }
                is ProfileData -> {
                    if (dataType.is_online == true) {
                        playersHashMap[it] = dataType
                        composable =
                            {
                                CustomPinImage(
                                    imageUri = if (dataType.image_uri == null) Uri.EMPTY else Uri.parse(
                                        dataType.image_uri
                                    ),
                                    true//userData.squad_id != null && "MYSQUADID" != null && userData.squad_id == "MYSQUADID"
                                )
                            }
                    }
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
                is HomeData -> {
                    oldData = _mapScreenState.value.home
                    _mapScreenState.value = _mapScreenState.value.copy(home = dataType)
                }
                is CustomPinData -> {
                    oldData = customPinsHashMap.put(it, dataType)

                }
                is ResourceData -> {
                    oldData = resourcesHashMap.put(it, dataType)
                }
                is TotemData -> {
                    oldData = totemsHashMap.put(it, dataType)

                }
                is ProfileData -> {
                    if (dataType.is_online == true) {
                        if (playersHashMap.containsKey(it))
                            oldData = playersHashMap.put(it, dataType)
                        else addPinHash(dataType)
                    } else removePinHash(dataType)

                }
            }
            if (oldData != null) {
                movePinConditionally(it, dataType.l, oldData.l)
            }
        }
    }

    private fun movePinConditionally(id: String, newGeoPoint: GeoPoint?, oldGeoPoint: GeoPoint?) {
        if (oldGeoPoint != null && newGeoPoint != null
            && !areGeoPointsEqual(newGeoPoint, oldGeoPoint)
        ) {
            movePinAtLatLng(id, newGeoPoint.latitude, newGeoPoint.longitude)
        }
    }


    private fun removePinHash(dataType: Data) {
        dataType.id?.let {
            when (dataType) {
                is HomeData -> {
                    _mapScreenState.value = mapScreenState.value.copy(home = HomeData())
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
            }
            removePin(it)
        }
    }


    private fun registerOnMapStateChangeListener() {
        var pinLocation =
            GeoPoint(INIT_SCROLL_LAT, INIT_SCROLL_LNG)
        var scale = _mapState.scale

        _mapState.setStateChangeListener {
            if (scale != this.scale) {
                scale = this.scale
                circleSize.value = MY_LOCATION_CIRCLE_SIZE.dp * this.scale / MAX_SCALE
            }

            val markerInfo = _mapState.getMarkerInfo(MY_PIN)
            if (markerInfo != null) {
                pinLocation = convertOffsetsToGeoPoint(
                    markerInfo.x,
                    markerInfo.y,
                    mapDimensions,
                    mapDimensions
                )
            }
            if (
                mapScreenState.value.followMe
                && mapScreenState.value.detectScroll
                && !areGeoPointsEqual(
                    pinLocation, convertOffsetsToGeoPoint(
                        this.centroidX,
                        this.centroidY,
                        mapDimensions,
                        mapDimensions
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
    }

    private fun initMyLocationPinAndRegisterMove() {
        _mapState.addLazyLoader(LAZY_LOADER_ID)
        _mapState.addMarker(
            MY_PIN,
            INIT_SCROLL_X,
            INIT_SCROLL_Y,
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
        )
    }


    private fun changeStateDetectScroll(shouldDetectScroll: Boolean) {
        _mapScreenState.value =
            _mapScreenState.value.copy(
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
                    selectedPlayer =
                    mapScreenState.value.playersHashMap[id]!!
                )
                showPlayerDialogHandler()

            } else if (mapScreenState.value.totemsHashMap.containsKey(id)) {


            } else if (mapScreenState.value.customPinsHashMap.containsKey(id)) {
                val customPin = mapScreenState.value.customPinsHashMap[id]!!
                showCustomPinDialogHandler(
                    l = customPin.l,
                    id = customPin.id,
                    placedBy = customPin.placed_by,
                    customPin.text
                )
            }
        }
    }

    private fun registerAddCustomPin() {//kako set to handluje?????????? u bazu
        viewModelScope.launch {
            _mapState.onLongPress { x, y ->
                showCustomPinDialogHandler(
                    convertOffsetsToGeoPoint(
                        x,
                        y,
                        mapDimensions,
                        mapDimensions
                    )
                )
            }
        }
    }


//    fun addCustomPin() {//jos jedan flow
//        val x = mapScreenState.value.pinDialog.x
//        val y = mapScreenState.value.pinDialog.y
//        addPinAtOffset(
//            pinId = CUSTOM + x + y,
//            x = x, y = y,
//            R.drawable.pin_custom
//        )
//    }

    private fun showCustomPinDialogHandler(
        l: GeoPoint?,
        id: String? = null,
        placedBy: String? = null,
        text: String? = null
    ) {
        _mapScreenState.value = _mapScreenState.value.copy(
            customPinDialog = _mapScreenState.value.customPinDialog.copy(
                id = id,
                dialogOpen = true,
                l = l!!,
                placedBy = placedBy,
                text = mutableStateOf(text ?: ""),
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

    private fun resetFiltersHandler() {
        viewModelScope.launch {
            filterResources.emit(false)
            filterTotems.emit(false)
            filterPlayers.emit(false)
            _mapScreenState.value =
                _mapScreenState.value.copy(
                    filterResources = false,
                    filterPlayers = false,
                    filterTotems = false,
                )
        }
    }

    //region firebase functions


    private fun addCustomPinFBHandler() {
        viewModelScope.launch {
            mapViewModelRepository.addCustomPin(
                l = mapScreenState.value.customPinDialog.l,
                visible_to = "AAAAAAAAAAAAA",//squad id||auth id
                placed_by = "BBBBBBBBBBB",//moj auth id
                text = mapScreenState.value.customPinDialog.text.value,
            )
        }
    }


    private fun updateCustomPinFBHandler() {
        viewModelScope.launch {
            mapScreenState.value.customPinDialog.id?.let {
                mapViewModelRepository.updateCustomPin(
                    id = it,
                    visible_to = "AAAAAAAAAAAAA",//,
                    placed_by = "BBBBBBBBBBB",//moj auth id
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
        viewModelScope.launch {
            mapViewModelRepository.addHome(
                "MOJID",
                GeoPoint(43.313198, 21.906673)
            )
        }
    }

    private fun updateHomeHandler() {}

    private fun removeHomeHandler() {
        viewModelScope.launch {
            mapViewModelRepository.deleteHome("MOJID")
        }
    }

    private fun updateResourceHandler(
        newCount: Int,
    ) {
        viewModelScope.launch {
            mapScreenState.value.selectedResource.id?.let { id ->
                mapViewModelRepository.updateResource(id, newCount)
            }
        }
    }


    private fun updateInventoryHandler(
        newUserInventoryData: UserInventoryData
    ) {
        viewModelScope.launch {
            mapScreenState.value.selectedResource.type?.let {
                mapViewModelRepository.updateUserInventory(
                    "VlYnaW2Mf5NuxzdnpYM2vBmckuE2",
                    newUserInventoryData
                )
            }
        }
    }


//endregion

}