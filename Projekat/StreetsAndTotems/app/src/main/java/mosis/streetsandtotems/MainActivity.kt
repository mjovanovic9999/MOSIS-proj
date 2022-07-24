package mosis.streetsandtotems

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import mosis.streetsandtotems.ui.theme.AppTheme
import ovh.plrapps.mapcompose.api.addMarker
import ovh.plrapps.mapcompose.ui.MapUI

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            AppTheme {
                MapContainer()
            }
        }

/*        val requestedepermisions=registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            isGranted:Boolean->
            if (isGranted) Log.i("AAAAAAAA","ok")
            else Log.i("AAAAAAAA","not ok")
        }*/
        reqPermission()

/*        this.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            Log.d("AAAAAAAAA",permissions.toString())
            permissions.entries.forEach {x->
                // check whether each permission is granted or not
                if(x.value==false) Log.i("nece", x.key)
                else Log.i("ocve",x.key+"KAO OCE")
            }
        }*/

    }

private fun reqPermission(){

    ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION)
}

}


@Composable
fun MapContainer(
    modifier: Modifier = Modifier.fillMaxHeight().fillMaxWidth(), viewModel: TestViewModel = hiltViewModel()
) {

    MapUI(modifier, state = viewModel.state)

    Button(onClick = {
        viewModel.state.addMarker("id", x = 0.5, y = 0.5) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier.size(50.dp),
            )
        }
    }) {
        Text(text = "Simple Button")
    }
}


@Composable
fun DefaultPreview() {
    AppTheme {
    }
}




@Composable
fun MapLayout(){
    AndroidView(
        factory = { context: Context ->
            val view = LayoutInflater.from(context)
                .inflate(R.layout.map, null, false)

            val btn= view.findViewById<Button>(R.id.button)

            btn.setOnClickListener{
                Toast.makeText(context,"aaaaaaaa",Toast.LENGTH_SHORT).show()
            }


            getInstance().load(context, androidx.preference.PreferenceManager.getDefaultSharedPreferences(context));
            val map=view.findViewById<MapView>(R.id.map)

            map.setTileSource(TileSourceFactory.MAPNIK)

            map.setMultiTouchControls(true)
            map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)








            val mapController = map.controller
            mapController.setZoom(context.getString(R.dimen.map_zoom).toDouble())




            val startPoint = GeoPoint( context.getString(R.dimen.nis_latitude).toDouble(),
                context.getString(R.dimen.nis_longitude).toDouble())

            mapController.setCenter(startPoint)

            // do whatever you want...

            val startMarker = Marker(map)
            startMarker.position = startPoint
            startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)


            startMarker.icon = AppCompatResources.getDrawable(context,R.drawable.ic_logo_only_tiki)
            startMarker.title = "Eve ni vammmmm";


            map.overlays.add(startMarker)




            val mReceive: MapEventsReceiver = object : MapEventsReceiver {
                override fun singleTapConfirmedHelper(p: GeoPoint): Boolean {
                /*    Toast.makeText(
                        context,
                        p.latitude.toString() + " - " + p.longitude,
                        Toast.LENGTH_LONG
                    ).show()*/
                    val startMarker = Marker(map)
                    startMarker.position = GeoPoint(p.latitude,p.longitude)
                    startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)


                    startMarker.icon = AppCompatResources.getDrawable(context,R.drawable.ic_pin_tiki)
                    startMarker.title = "Eve ni vammmmm";
                    map.overlays.add(startMarker)


                    return false
                }

                override fun longPressHelper(p: GeoPoint): Boolean {
                    return false
                }
            }

            map.getOverlays().add(MapEventsOverlay(mReceive));


            view // return the view
        },
        update = { it ->

             }
    )

}

