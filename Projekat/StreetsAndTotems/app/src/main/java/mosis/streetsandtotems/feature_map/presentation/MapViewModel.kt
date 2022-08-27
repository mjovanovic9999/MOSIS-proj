package mosis.streetsandtotems.feature_map.presentation

import android.app.Application
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
import mosis.streetsandtotems.core.PinConstants.CUSTOM
import mosis.streetsandtotems.core.PinConstants.FRIENDS
import mosis.streetsandtotems.core.PinConstants.LAZY_LOADER_ID
import mosis.streetsandtotems.core.PinConstants.MY_PIN
import mosis.streetsandtotems.core.PinConstants.MY_PIN_COLOR
import mosis.streetsandtotems.core.PinConstants.MY_PIN_COLOR_OPACITY
import mosis.streetsandtotems.core.PinConstants.MY_PIN_RADIUS
import mosis.streetsandtotems.core.PinConstants.RESOURCES_BRICKS
import mosis.streetsandtotems.core.PinConstants.RESOURCES_EMERALDS
import mosis.streetsandtotems.core.PinConstants.RESOURCES_STONES
import mosis.streetsandtotems.core.PinConstants.TOTEMS
import mosis.streetsandtotems.feature_map.domain.model.PinDTO
import mosis.streetsandtotems.feature_map.domain.util.PinTypes
import mosis.streetsandtotems.feature_map.domain.util.detectPinType
import mosis.streetsandtotems.feature_map.presentation.components.CustomPin
import mosis.streetsandtotems.feature_map.presentation.components.CustomPinImage
import mosis.streetsandtotems.feature_map.presentation.util.areOffsetsEqual
import mosis.streetsandtotems.feature_map.presentation.util.calculateMapDimensions
import mosis.streetsandtotems.feature_map.presentation.util.convertLatLngToOffsets
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
            customPinDialogOpen = false,
            playerDialogOpen = false,
            filterDialogOpen = false,
            followMe = true,
            detectScroll = true,
            filtersFlow = MutableStateFlow<Set<PinTypes>>(setOf<PinTypes>()),
            pinsFlow = MutableStateFlow(
                setOf(
                    PinDTO(RESOURCES_BRICKS, (INIT_SCROLL_LAT * 1.0001), INIT_SCROLL_LNG),
                    PinDTO(RESOURCES_STONES, (INIT_SCROLL_LAT), INIT_SCROLL_LNG * 1.0001),
                    PinDTO(FRIENDS, (INIT_SCROLL_LAT * 1.0001), INIT_SCROLL_LNG * 1.0001),
                    PinDTO(TOTEMS, (INIT_SCROLL_LAT * 1.00005), INIT_SCROLL_LNG * 1.00005),
                )
            )

        ))

        mapScreenState = _mapScreenState
        _mapState = mapScreenState.value.mapState.value


        initMyLocationPinAndRegisterMove()

        registerOnMapStateChangeListener()

        registerFilterPins()

        registerAddCustomPin()

        _mapState.addMarker(
            "AAAAAAAAAA",
            INIT_SCROLL_X * 0.99999,
            INIT_SCROLL_Y * 0.99999,
            c = {
                CustomPinImage(imageUri = "https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fstatic.onecms.io%2Fwp-content%2Fuploads%2Fsites%2F13%2F2015%2F04%2F05%2Ffeatured.jpg")
            },
            clipShape = null,
            renderingStrategy = RenderingStrategy.LazyLoading(LAZY_LOADER_ID)
        )
    }

    private fun registerOnMapStateChangeListener() {
        var pinLocation =
            arrayOf(INIT_SCROLL_X, INIT_SCROLL_Y)

        var scale = _mapState.scale

        _mapState.setStateChangeListener {
            if (scale != this.scale) {
                scale = this.scale
                circleSize.value = MY_LOCATION_CIRCLE_SIZE.dp * this.scale / MAX_SCALE
            }

            val markerInfo = _mapState.getMarkerInfo(MY_PIN)
            if (markerInfo != null) {
                pinLocation = arrayOf(
                    markerInfo.x,
                    markerInfo.y
                )
            }
            if (
                mapScreenState.value.followMe
                && mapScreenState.value.detectScroll
                &&
                (!areOffsetsEqual(pinLocation[0], this.centroidX)
                        || !areOffsetsEqual(pinLocation[1], this.centroidY))
            ) {
                _mapScreenState.value = _mapScreenState.value.copy(
                    followMe = false
                )
            }
        }
    }

    private fun registerFilterPins() {
        viewModelScope.launch {
//            var oldPins:Set<PinDTO>//s ovo da se izbace nepotrebni pinovi od mapu
            mapScreenState.value.pinsFlow.combineTransform(mapScreenState.value.filtersFlow) { pins, filters ->

                //_mapState.removeAllMarkers()//for each not in pins

                for (pinDTO in pins) {
                    if (filters.contains(detectPinType(pinDTO.id))
                        || (filters.contains(PinTypes.TypeResource) && (detectPinType(pinDTO.id) is PinTypes.ITypeResource))
                    ) {
                        removePin(pinDTO.id)
                    } else {
                        if (pinChangedLocation(pinDTO))
                            movePinAtLatLng(pinDTO.id, pinDTO.lat, pinDTO.lng)
                        else {
                            emit(pinDTO)
                        }
                    }
                }
            }.collect {
                addPinDTO(it)
            }
        }
    }

    private fun pinChangedLocation(pinDTO: PinDTO): Boolean {
        val markerInfo = _mapState.getMarkerInfo(pinDTO.id)
        if (markerInfo != null) {
            val cords = convertLatLngToOffsets(pinDTO.lat, pinDTO.lng, mapDimensions, mapDimensions)
            return !(areOffsetsEqual(markerInfo.x, cords[0])
                    && areOffsetsEqual(markerInfo.y, cords[1]))
        }
        return false
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

            )

        viewModelScope.launch {
            LocationService.mLocation.collectLatest {
                if (it != null) {
                    val latLon = convertLatLngToOffsets(
                        it.latitude,
                        it.longitude,
                        mapDimensions,
                        mapDimensions
                    )

                    movePinAtOffset(MY_PIN, latLon[0], latLon[1])

                    if (mapScreenState.value.followMe) {
                        changeStateDetectScroll(false)

                        centerMeOnMyPinSuspend()

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

    private fun registerAddCustomPin() {
        viewModelScope.launch {
            _mapState.onLongPress { x, y ->
                addPinAtOffset(CUSTOM + x.toString() + y.toString(), x, y)
            }
        }
    }

    fun followMe() {
        viewModelScope.launch {
            centerMeOnMyPinSuspend()
            _mapScreenState.value = _mapScreenState.value.copy(followMe = true, detectScroll = true)
        }
    }

    private suspend fun centerMeOnMyPinSuspend() {
        val markerInfo = _mapState.getMarkerInfo(MY_PIN)
        if (markerInfo != null) {
            val x = viewModelScope.launch {
                _mapState.centerOnMarker(MY_PIN)
            }
            x.join()
        }
    }

    private fun centerMeOnMyPin() {
        centerOnPin(MY_PIN)
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
    ) {
        _mapState.addMarker(
            pinId,
            x,
            y,
            c = {
                CustomPin(pinId)
            },
            clipShape = null,
            relativeOffset = if (detectPinType(pinId) == PinTypes.TypeHomeDiscoveryShot)
                Offset(-.5f, -.5f)
            else
                Offset(-.5f, -1f),
            renderingStrategy = RenderingStrategy.LazyLoading(LAZY_LOADER_ID)
        )
    }

    fun addPinDTO(pin: PinDTO) {
        addPinAtLatLng(pin.id, pin.lat, pin.lng)
    }

    fun addPinAtLatLng(
        pinId: String,
        lat: Double,
        lng: Double,
    ) {
        val cords = convertLatLngToOffsets(lat, lng, mapDimensions, mapDimensions)
        addPinAtOffset(pinId, cords[0], cords[1])
    }

    fun movePinAtOffset(pinId: String, x: Double, y: Double) {
        _mapState.moveMarker(
            pinId,
            x,
            y,
        )
    }

    fun movePinAtLatLng(pinId: String, lat: Double, lng: Double) {
        val cords = convertLatLngToOffsets(lat, lng, mapDimensions, mapDimensions)
        movePinAtOffset(pinId, cords[0], cords[1])
    }

    fun removePin(pinId: String) {//check
        _mapState.removeMarker(pinId)
    }


    fun showCustomPinDialog() {
        _mapScreenState.value = _mapScreenState.value.copy(customPinDialogOpen = true)
    }

    fun closeCustomPinDialog() {
        _mapScreenState.value = _mapScreenState.value.copy(customPinDialogOpen = false)
    }

    fun showPlayerDialog() {
        _mapScreenState.value = _mapScreenState.value.copy(playerDialogOpen = true)
    }

    fun closePlayerDialog() {
        _mapScreenState.value = _mapScreenState.value.copy(playerDialogOpen = false)
    }

    fun showFilterDialog() {
        _mapScreenState.value = _mapScreenState.value.copy(filterDialogOpen = true)
    }

    fun closeFilterDialog() {
        _mapScreenState.value = _mapScreenState.value.copy(filterDialogOpen = false)
    }

    fun updateFilter(pinType: PinTypes) {
        mapScreenState.value.filtersFlow.update {
            if (it.contains(pinType)) it.minus(pinType)
            else it.plus(pinType)
        }
    }

    fun resetFilters() {
        mapScreenState.value.filtersFlow.update { setOf() }
    }

    fun tempAddDDPIN() {
        _mapScreenState.value.pinsFlow.update {
            setOf(
                PinDTO(RESOURCES_EMERALDS, (INIT_SCROLL_LAT * 1.0002), INIT_SCROLL_LNG),
                PinDTO(RESOURCES_BRICKS, (INIT_SCROLL_LAT), INIT_SCROLL_LNG * 1.0002),
                PinDTO(TOTEMS, (INIT_SCROLL_LAT * 1.0002), INIT_SCROLL_LNG * 1.0002),
                PinDTO(FRIENDS, (INIT_SCROLL_LAT * 1.00007), INIT_SCROLL_LNG * 1.00006)
            )
        }
    }

}

