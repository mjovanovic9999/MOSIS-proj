package mosis.streetsandtotems.feature_map.presentation

import android.app.Application
import android.util.Log
import android.widget.Toast
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.MapConstants.INIT_SCROLL_X
import mosis.streetsandtotems.core.MapConstants.INIT_SCROLL_Y
import mosis.streetsandtotems.core.MapConstants.LEVEL_COUNT
import mosis.streetsandtotems.core.MapConstants.MAX_SCALE
import mosis.streetsandtotems.core.MapConstants.MY_LOCATION_CIRCLE_SIZE
import mosis.streetsandtotems.core.MapConstants.TILE_KEY
import mosis.streetsandtotems.core.MapConstants.TILE_URL_512
import mosis.streetsandtotems.core.MapConstants.TITLE_SIZE
import mosis.streetsandtotems.core.MapConstants.WORKER_COUNT
import mosis.streetsandtotems.core.PinConstants.MY_PIN
import mosis.streetsandtotems.core.PinConstants.MY_PIN_COLOR
import mosis.streetsandtotems.core.PinConstants.MY_PIN_COLOR_OPACITY
import mosis.streetsandtotems.core.PinConstants.MY_PIN_RADIUS
import mosis.streetsandtotems.feature_map.presentation.components.Pin
import mosis.streetsandtotems.feature_map.presentation.util.areOffsetsEqual
import mosis.streetsandtotems.feature_map.presentation.util.calculateMapDimensions
import mosis.streetsandtotems.feature_map.presentation.util.convertLatLngToOffsets
import mosis.streetsandtotems.services.LocationService
import ovh.plrapps.mapcompose.api.*
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.layout.Fill
import ovh.plrapps.mapcompose.ui.state.MapState
import java.io.FileInputStream
import javax.inject.Inject
import kotlin.math.roundToInt

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
            followMe = true,
            detectScroll = false,
        ))

        mapScreenState = _mapScreenState
        _mapState = mapScreenState.value.mapState.value

        initMyLocationPinAndRegisterMove()


        var pinLocation =
            arrayOf(INIT_SCROLL_X, INIT_SCROLL_Y)

        _mapState.setStateChangeListener {
            //neje optimizovano
            circleSize.value = MY_LOCATION_CIRCLE_SIZE.dp * this.scale / MAX_SCALE


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

        registerAddCustomPin()


////STA JE REFERRENTIALSNAPSHOTFLOW
//        //IMA U SERVIS "STA JE OVO"
//

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


    private fun initMyLocationPinAndRegisterMove() {
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
            relativeOffset = Offset(-.5f, -.5f)
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

                    movePinAt(MY_PIN, latLon[0], latLon[1])

                    if (mapScreenState.value.followMe) {
                        changeStateDetectScroll(false)

                        centerMeOnMyMarkerSuspend()

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
                _mapState.addMarker(
                    x.toString() + y.toString(),
                    x,
                    y,
                    c = {
                        Pin(R.drawable.pin_base_discovery_shot)
                    },
                    clipShape = null,
                )
            }
        }
    }

    fun followMe() {
        centerMeOnMyMarker()
        _mapScreenState.value = _mapScreenState.value.copy(followMe = true, detectScroll = false)

    }

    private suspend fun centerMeOnMyMarkerSuspend() {
        val markerInfo = _mapState.getMarkerInfo(MY_PIN)
        if (markerInfo != null) {
            val x = viewModelScope.launch {
                _mapState.centerOnMarker(MY_PIN)
            }
            x.join()
        }
    }

    private fun centerMeOnMyMarker() {
        val markerInfo = _mapState.getMarkerInfo(MY_PIN)
        if (markerInfo != null) {
            val x = viewModelScope.launch {
                _mapState.centerOnMarker(MY_PIN)
            }
        }
    }

    fun movePinAt(id: String, x: Double, y: Double) {
        _mapState.moveMarker(
            id,
            x,
            y,
        )
    }
}

