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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
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
import mosis.streetsandtotems.feature_map.domain.model.*
import mosis.streetsandtotems.feature_map.domain.repository.MapViewModelRepository
import mosis.streetsandtotems.feature_map.presentation.components.CustomPin
import mosis.streetsandtotems.feature_map.presentation.components.CustomPinImage
import mosis.streetsandtotems.feature_map.presentation.util.*
import mosis.streetsandtotems.services.LocationService
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
    private val mapViewModelRepository: MapViewModelRepository
) : ViewModel() {
    private val _mapScreenState: MutableState<MapScreenState>
    private val _mapState: MapState
    val mapScreenState: State<MapScreenState>

    private val mapDimensions = calculateMapDimensions()
    private val circleSize = mutableStateOf(MY_LOCATION_CIRCLE_SIZE.dp / MAX_SCALE)


    init {
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
        _mapScreenState = mutableStateOf(MapScreenState(
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
            filterPlayers = MutableStateFlow(false),
            filterTotems = MutableStateFlow(false),
            filterResources = MutableStateFlow(false),
            resourcesHashMap = mutableMapOf(),
            playersHashMap = mutableMapOf(),
            totemsHashMap = mutableMapOf(),
            customPinsHashMap = mutableMapOf(),
            selectedPlayer = mutableStateOf(UserInGameData()),
            home = mutableStateOf(HomeData())
        ))

        mapScreenState = _mapScreenState
        _mapState = mapScreenState.value.mapState.value


        initMyLocationPinAndRegisterMove()

        registerOnMapStateChangeListener()

        registerFilterPins()

        registerAddCustomPin()

        registerAllPinsFlow()

        registerOnPinClick()
    }

//    fun onEvent(event: MapViewModelEvents){
//        when(event){
//
//        }
//    }

    private fun registerAllPinsFlow() {
        registerPinsFlow(LocationService.playersPinFlow)
        registerPinsFlow(LocationService.totemsPinsFlow)
        registerPinsFlow(LocationService.resourcesPinsFlow)
        registerPinsFlow(LocationService.customPinsFlow)
        registerPinsFlow(LocationService.homePinsFlow)
    }

    private fun <T : IData> registerPinsFlow(flow: MutableSharedFlow<PinAction<T>?>) {
        viewModelScope.launch {
            flow.collect {
                Log.d("tag", "register " + it?.pinData)
                when (it?.action) {
                    PinActionType.Added -> addPinHash(it.pinData)
                    PinActionType.Modified -> modifyPinHash(it.pinData)
                    PinActionType.Removed -> removePinHash(it.pinData)
                    null -> Log.d("tag", "null resource")
                }
            }
        }
    }

    private fun addPinHash(dataType: IData) {
        dataType.id?.let {
            var composable: @Composable() (() -> Unit)? = null
            when (dataType) {
                is HomeData -> {
                    _mapScreenState.value.home.value = dataType
                    composable = { CustomPin(resourceId = R.drawable.pin_home) }
                }
                is CustomPinData -> {
                    _mapScreenState.value.customPinsHashMap[it] = dataType
                    composable = { CustomPin(resourceId = R.drawable.pin_custom) }
                }
                is ResourceData -> {
                    _mapScreenState.value.resourcesHashMap[it] = dataType
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
                    _mapScreenState.value.totemsHashMap[it] = dataType
                    composable = { CustomPin(resourceId = R.drawable.pin_tiki) }
                }
                is UserInGameData -> {
                    _mapScreenState.value.playersHashMap[it] = dataType
                    composable =
                        {
                            CustomPinImage(
                                imageUri = dataType.user_profile_data?.image ?: Uri.EMPTY,
                                true//userData.squad_id != null && "MYSQUADID" != null && userData.squad_id == "MYSQUADID"
                            )
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

    private fun modifyPinHash(dataType: IData) {
        dataType.id?.let {
            var oldData: IData? = null
            when (dataType) {
                is HomeData -> {
                    oldData = _mapScreenState.value.home.value
                    _mapScreenState.value.home.value = dataType
                }
                is CustomPinData -> {
                    oldData = _mapScreenState.value.customPinsHashMap.put(it, dataType)

                }
                is ResourceData -> {
                    oldData = _mapScreenState.value.resourcesHashMap.put(it, dataType)
                }
                is TotemData -> {
                    oldData = _mapScreenState.value.totemsHashMap.put(it, dataType)

                }
                is UserInGameData -> {
                    oldData = _mapScreenState.value.playersHashMap.put(it, dataType)
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


    private fun removePinHash(dataType: IData) {
        dataType.id?.let {
            when (dataType) {
                is HomeData -> {
                    mapScreenState.value.home.value = HomeData()
                }
                is CustomPinData -> {
                    mapScreenState.value.customPinsHashMap.remove(it)
                }
                is ResourceData -> {
                    mapScreenState.value.resourcesHashMap.remove(it)
                }
                is TotemData -> {
                    mapScreenState.value.totemsHashMap.remove(it)
                }
                is UserInGameData -> {
                    mapScreenState.value.playersHashMap.remove(it)
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
            mapScreenState.value.filterResources.collect { shouldFilter ->
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
            mapScreenState.value.filterPlayers.collect { shouldFilter ->
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
            mapScreenState.value.filterTotems.collect { shouldFilter ->
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

        viewModelScope.launch {
            LocationService.locationFlow.collectLatest {
                if (it != null) {
                    movePinAtLatLng(MY_PIN, it.latitude, it.longitude)

                    if (mapScreenState.value.followMe) {
                        changeStateDetectScroll(false)

                        centerMeOnMyPin()

                        changeStateDetectScroll(true)
                    }
                }

            }
        }
    }


    private fun changeStateDetectScroll(shouldDetectScroll: Boolean) {
        _mapScreenState.value =
            _mapScreenState.value.copy(
                detectScroll = shouldDetectScroll
            )
    }


    fun followMe() {
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


    fun centerOnPin(pinId: String) {
        val markerInfo = _mapState.getMarkerInfo(pinId)
        if (markerInfo != null) {
            viewModelScope.launch {
                _mapState.centerOnMarker(pinId)
            }
        }
    }

    @OptIn(ExperimentalClusteringApi::class)
    fun addPinAtOffset(
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


    fun movePinAtLatLng(pinId: String, lat: Double, lng: Double) {
        val cords = convertLatLngToOffsets(lat, lng, mapDimensions, mapDimensions)
        _mapState.moveMarker(
            pinId,
            cords[0],
            cords[1],
        )
    }

    fun removePin(pinId: String) {
        _mapState.removeMarker(pinId)
    }

    fun registerOnPinClick() {
        _mapState.onMarkerClick { id, x, y ->
            Log.d("tag", "clicked na pin $id x je $x y je $y")
            if (mapScreenState.value.resourcesHashMap.containsKey(id)) {

            } else if (mapScreenState.value.playersHashMap.containsKey(id)) {
                _mapScreenState.value.selectedPlayer.value =
                    mapScreenState.value.playersHashMap[id]!!
                Log.d("tag", _mapScreenState.value.selectedPlayer.value.toString())
                showPlayerDialog()

            } else if (mapScreenState.value.totemsHashMap.containsKey(id)) {


            } else if (mapScreenState.value.customPinsHashMap.containsKey(id)) {
                val customPin = mapScreenState.value.customPinsHashMap[id]!!
                showCustomPinDialog(
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
                showCustomPinDialog(convertOffsetsToGeoPoint(x, y, mapDimensions, mapDimensions))
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

    private fun showCustomPinDialog(
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

    fun closeCustomPinDialog() {
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

    fun showPlayerDialog() {
        _mapScreenState.value = _mapScreenState.value.copy(playerDialogOpen = true)
    }

    fun closePlayerDialog() {
        _mapScreenState.value = _mapScreenState.value.copy(playerDialogOpen = false)
//        _mapScreenState.value.selectedPlayer.value = UserInGameData()//treba li ovo?????
    }

    fun showFilterDialog() {
        _mapScreenState.value = _mapScreenState.value.copy(filterDialogOpen = true)
    }

    fun closeFilterDialog() {
        _mapScreenState.value = _mapScreenState.value.copy(filterDialogOpen = false)
    }

    fun updateFilterResources() {
        mapScreenState.value.filterResources.value = !mapScreenState.value.filterResources.value
    }

    fun updateFilterFriends() {
        mapScreenState.value.filterPlayers.value = !mapScreenState.value.filterPlayers.value
    }

    fun updateFilterTotems() {
        mapScreenState.value.filterTotems.value = !mapScreenState.value.filterTotems.value
    }

    fun resetFilters() {
        mapScreenState.value.filterResources.value = false
        mapScreenState.value.filterPlayers.value = false
        mapScreenState.value.filterTotems.value = false
    }

    //region firebase functions

    fun addCustomPinFB() {
        viewModelScope.launch {
            mapViewModelRepository.addCustomPin(
                l = mapScreenState.value.customPinDialog.l,
                visible_to = "AAAAAAAAAAAAA",//squad id||auth id
                placed_by = "BBBBBBBBBBB",//moj auth id
                text = mapScreenState.value.customPinDialog.text.value,
            )
        }
    }

    fun updateCustomPinFB() {
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

    fun deleteCustomPin() {
        viewModelScope.launch {
            mapScreenState.value.customPinDialog.id?.let {
                mapViewModelRepository.deleteCustomPin(it)
            }
        }
    }


    fun addHome() {
        viewModelScope.launch {
            mapViewModelRepository.addHome(
                "MOJID",
                GeoPoint(43.313198, 21.906673)
            )
        }
    }

    fun updateHome() {}

    fun deleteHome() {
        viewModelScope.launch {
            mapViewModelRepository.deleteHome("MOJID")
        }
    }

    //endregion
}