package mosis.streetsandtotems.feature_map.presentation

import android.app.Application
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import mosis.streetsandtotems.feature_map.domain.LocationDTO
import mosis.streetsandtotems.feature_map.domain.LocationTracker
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.enableRotation
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.state.MapState
import java.io.FileInputStream
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val appContext: Application,
    private val locationTracker: LocationTracker
) : ViewModel() {

    val tileStreamProvider = TileStreamProvider { row, col, zoomLvl ->
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
    val mapState: MapState by mutableStateOf(
        MapState(
            19,
            67108864,
            67108864,
            workerCount = 16
        ) {
            scale(0.25f)
            scroll(0.560824, 0.366227)
        }.apply {
            addLayer(tileStreamProvider)
            enableRotation()
        }
    )


    var locationState by mutableStateOf(LocationDTO(-1.0, -1.0, -1.0f))
        private set

    fun LoadLocation(cb: () -> Unit) {
        viewModelScope.launch {
            locationTracker.getCurrentLocation()?.let { location ->
                locationState =
                    locationState.copy(location.latitude, location.longitude, location.accuracy)
                cb()
            } ?: kotlin.run {
                locationState = locationState.copy(-1.1, -1.1, -1.1f)
            }
        }
    }


}
