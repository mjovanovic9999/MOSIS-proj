package mosis.streetsandtotems.feature_map.presentation.map

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.Resource
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
    val state: MapState by mutableStateOf(
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


    var locationState by mutableStateOf(LocationDTO(-1.0, -1.0))
        private set

    fun LoadLocation() {
        viewModelScope.launch {
            locationTracker.getCurrentLocation()?.let { location ->
                locationState = locationState.copy(location.latitude, location.longitude)

            } ?: kotlin.run {
                locationState = locationState.copy(-1.1, -1.1)
            }
        }
    }
}


/*.pointerInput(Unit) {
        detectTapGestures(onTap = {
            viewModel.state.addMarker("id", x =it.x.toDouble(), y = it.y.toDouble() ) {
                Image(
                    painter=painterResource(R.drawable.pin_tiki),
                    contentDescription = null,
                    modifier = Modifier.size(50.dp),
                )
                Toast.makeText(LocalContext.current, it.x.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText(LocalContext.current, it.y.toString(), Toast.LENGTH_SHORT).show()
            }})
    }*/