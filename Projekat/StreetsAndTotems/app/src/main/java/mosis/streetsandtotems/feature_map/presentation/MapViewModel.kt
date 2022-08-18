package mosis.streetsandtotems.feature_map.presentation

import android.app.Application
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import mosis.streetsandtotems.R
import mosis.streetsandtotems.core.MapConstants.initScale
import mosis.streetsandtotems.core.MapConstants.initScrollX
import mosis.streetsandtotems.core.MapConstants.initScrollY
import mosis.streetsandtotems.core.MapConstants.levelCount
import mosis.streetsandtotems.core.MapConstants.tileSize
import mosis.streetsandtotems.core.MapConstants.workerCount
import mosis.streetsandtotems.core.PinConstants.MY_PIN
import mosis.streetsandtotems.feature_map.presentation.components.Pin
import mosis.streetsandtotems.feature_map.presentation.util.calculateMapDimensions
import mosis.streetsandtotems.feature_map.presentation.util.latLonToOffsets
import mosis.streetsandtotems.services.LocationService
import ovh.plrapps.mapcompose.api.*
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.state.MapState
import java.io.FileInputStream
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val appContext: Application,
) : ViewModel() {
    private val _mapScreenState: MutableState<MapScreenState>
    private val _mapState: MapState
    val mapScreenState: State<MapScreenState>

    private val mapDimensions = calculateMapDimensions()

    init {
        val tileStreamProvider = TileStreamProvider { row, col, zoomLvl ->
            try {
                val image = Glide.with(appContext)
                    .downloadOnly()
                    //   .load("https://api.maptiler.com/maps/streets/256/${zoomLvl}/${col}/${row}.png?key=HqIvIaAAnQt3ibV6COHi")
                    //  .load("https://api.maptiler.com/maps/openstreetmap/${zoomLvl}/${col}/${row}.jpg?key=njA6yIfsMq23cZHLTop1")
                    .load("https://api.maptiler.com/maps/openstreetmap/256/${zoomLvl}/${col}/${row}.jpg?key=njA6yIfsMq23cZHLTop1")
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
                    levelCount = levelCount,
                    fullWidth = mapDimensions,
                    fullHeight = mapDimensions,
                    workerCount = workerCount,
                    tileSize = tileSize
                ) {
                    scale(initScale)
                    scroll(initScrollX, initScrollY)
                }.apply {
                    addLayer(tileStreamProvider)
                    enableRotation()
                }
            ),
            customPinDialogOpen = false,
            playerDialogOpen = false,
        ))

        mapScreenState = _mapScreenState
        _mapState = mapScreenState.value.mapState.value

        addPin(
            MY_PIN,
            initScrollX,
            initScrollY,
            R.drawable.pin_full_circle,
            Offset(-.5f, -.5f),
        )

        viewModelScope.launch {

            _mapState.onTap { x, y ->

                _mapState.addMarker(
                    "x",
                    x,
                    y,
                    c = { Pin(resourceId = R.drawable.pin_base_discovery_shot) },
                    relativeOffset = Offset(-.5f, -.5f)
                )
            }

            LocationService.mLocation.collectLatest {
                Log.d(
                    "tag",
                    "NEW LOCATION FLOW: ${it?.latitude}, ${it?.longitude}, ${it?.accuracy}"
                )
                if (it != null) {
                    val latLon = latLonToOffsets(
                        it.latitude,
                        it.longitude,
                        mapDimensions,
                        mapDimensions
                    )
//                    _mapState.moveMarker(
//                        MY_PIN,
//                        latLon[0],
//                        latLon[1],
//                    )
                }
            }
        }
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

    fun locateMe() {
        val markerInfo = _mapState.getMarkerInfo(MY_PIN)
        if (markerInfo != null && _mapState.scroll != Offset(
                markerInfo.x.toFloat(),
                markerInfo.y.toFloat()
            )
        ) {
            viewModelScope.launch {
                _mapState.centerOnMarker(MY_PIN)
            }
        }
    }

    fun addPin(
        id: String,
        x: Double,
        y: Double,
        resourceId: Int,
        relativeOffset: Offset = Offset(-.5f, -1f)
    ) {
        _mapState.addMarker(
            id,
            x,
            y,
            c = {
//                Box(
//                    Modifier
//                        .size(48.dp)
//                        .background(Color(0x551a88e9), shape = CircleShape)
//                ) {
////                    Image(
////                        painter = painterResource(resourceId),
////                        contentDescription = null,
////                        modifier = Modifier
//////                            .height(20.dp)
//////                            .align(Alignment.Center)
//////                            .offset(10.dp, 10.dp),
////                    )
//                    Image(
//                        painter = painterResource(R.drawable.pin_full_circle),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .height(16.dp)
//                            .align(Alignment.Center),
//                    )

                //    }
//                Box(
//                    Modifier
//                        .size(48.dp)
//                        .background(Color(0x551a88e9), shape = CircleShape)
//                ) {
                Icon(
                    imageVector = Icons.Filled.Circle,
                    contentDescription = null,
                    tint = Color(0xFF1a88e9),

                    modifier = Modifier
                        .size(20.dp)
                        .background(
                            (Color(0x551a88e9)),
                            CircleShape
                        ).border(100.dp,Color(0x551a88e9), CircleShape)
                )
                //}
            },
            clipShape = null,
            relativeOffset = relativeOffset
        )
    }

    fun movePin(id: String, x: Double, y: Double) {
        _mapState.moveMarker(
            MY_PIN,
            x,
            y,
        )
    }


//    fun addPin() {
//
//    //    val long = (mapScreenState.value.myCoordinates.value?.longitude ?: .0)
//      //  val lat = (mapScreenState.value.myCoordinates.value?.latitude ?: .0)
//
//        Log.d("tag x", lat.toString())
//        Log.d("tag y", long.toString())
//        val niz = doProjection(lat, long)
//        Log.d("tag x", niz[0].toString())
//        Log.d("tag y", niz[1].toString())
////        _mapState.onTap { x, y ->
//
//        _mapState.addMarker(
//
//            //    (niz[0] + niz[1]).toString(),
//
//            MY_PIN,
//            niz[0],
//            niz[1],
//            c = { Pin(R.drawable.pin_emerald) },
//            clipShape = null
//
//        )
//        viewModelScope.launch {
//            _mapState.centerOnMarker(MY_PIN)
//        }
////        }
//    }

//    fun CenterMeOnMap() {
//
//        viewModelScope.launch {
//            _mapState.centerOnMarker(
//                MY_PIN,
//                0.4f
//            )
//        }
//    }
}

