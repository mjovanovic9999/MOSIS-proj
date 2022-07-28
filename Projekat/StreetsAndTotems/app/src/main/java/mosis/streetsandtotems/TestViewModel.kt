package mosis.streetsandtotems

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.lifecycle.HiltViewModel
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.enableRotation
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.state.MapState
import java.io.BufferedInputStream
import java.io.ByteArrayInputStream
import java.io.FileInputStream
import java.net.HttpURLConnection
import java.net.URL
import java.nio.ByteBuffer
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(private val appContext: Application) : ViewModel() {

    val tileStreamProvider = TileStreamProvider { row, col, zoomLvl ->
        try {
            val image = Glide.with(appContext)
                                    .downloadOnly()
                                    .load( "https://api.maptiler.com/maps/streets/256/${zoomLvl}/${col}/${row}.png?key=HqIvIaAAnQt3ibV6COHi")
                                    .submit()
                                    .get()


            FileInputStream(image)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }}
    val state: MapState by mutableStateOf(
        MapState(19, 67108864, 67108864, workerCount = 16) {
            scale(0f)
        }.apply {
            addLayer(tileStreamProvider)
            enableRotation()
        }
    )

}