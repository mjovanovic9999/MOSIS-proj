package mosis.streetsandtotems

import android.app.Application
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
import java.net.HttpURLConnection
import java.net.URL
import java.nio.ByteBuffer
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(private val appContext: Application) : ViewModel() {

   /* val tileStreamProvider =
        TileStreamProvider { row, col, zoomLvl ->

            val bitmapTarget = Glide.with(appContext).asBitmap().load( "https://www.thecrazyprogrammer.com/wp-content/uploads/2015/09/Neeraj-Mishra.png").submit(24,24)
            val bitmap = bitmapTarget.get()
            var byteBuffer = ByteBuffer.allocate(bitmap.allocationByteCount)
            bitmap.copyPixelsToBuffer(byteBuffer)
            val byteArray = ByteArrayInputStream(byteBuffer.array())
            Glide.with(appContext).clear(bitmapTarget);

            byteArray
        }*/

    val tileStreamProvider = TileStreamProvider { row, col, zoomLvl ->
        try {
            val url = URL("https://api.maptiler.com/maps/basic-v2/256/${zoomLvl}/${col}/${row}.png?key=HqIvIaAAnQt3ibV6COHi")
            val connection = url.openConnection() as HttpURLConnection
            connection.setRequestProperty("User-Agent","Android");
            connection.doInput = true
            connection.connect()
            BufferedInputStream(connection.inputStream)
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