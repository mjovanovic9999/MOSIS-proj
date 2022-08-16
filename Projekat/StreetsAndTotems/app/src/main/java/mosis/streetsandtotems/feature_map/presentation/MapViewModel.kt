package mosis.streetsandtotems.feature_map.presentation

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.lifecycle.HiltViewModel
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.enableRotation
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.state.MapState
import java.io.FileInputStream
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val appContext: Application,
) : ViewModel() {
    private val tileStreamProvider = TileStreamProvider { row, col, zoomLvl ->
        try {
            val image = Glide.with(appContext)
                .downloadOnly()
                .load("https://api.maptiler.com/maps/streets/256/${zoomLvl}/${col}/${row}.png?key=HqIvIaAAnQt3ibV6COHi")
                .submit()
                .get()


            FileInputStream(image)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private val _mapScreenState =
        mutableStateOf(MapScreenState(
            mapState = mutableStateOf(
                MapState(
                    levelCount = 19,
                    fullWidth = 67108864,
                    fullHeight = 67108864,
                    workerCount = 32
                ) {
                    scale(0.25f)
                    scroll(0.560824, 0.366227)
                }.apply {
                    addLayer(tileStreamProvider)
                    enableRotation()
                }
            ),
            customPinDialogOpen = false,
            playerDialogOpen = false))
    val mapScreenState: State<MapScreenState> = _mapScreenState

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
}
