package mosis.streetsandtotems

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import mosis.streetsandtotems.ui.theme.AppTheme
import org.osmdroid.config.Configuration.*
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            AppTheme {
              //  window.navigationBarColor = color // Set color of system navigationBar same as BottomNavigationView
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Test")
                    MapLayout()
/*                    OnLifecycleEvent { owner, event ->
                        // do stuff on event
                        when (event) {
                            Lifecycle.Event.ON_RESUME -> {
                                Toast.makeText(this,"resume",Toast.LENGTH_SHORT).show()
                                       val btn= findViewById<Button>(R.id.button)
                                       btn.setOnClickListener{
                                           Toast.makeText(this,"aaaaaaaa",Toast.LENGTH_SHORT).show()
                                           val per= Array<String>(5) { "it = $it" }
                                           per[0] = Manifest.permission.ACCESS_FINE_LOCATION

                                           per[1] = Manifest.permission.ACCESS_COARSE_LOCATION
                                           per[2] = Manifest.permission.CAMERA
                                           ActivityCompat.requestPermissions(this,per,1)


                                       }
                                }
                            else                      -> { *//* other stuff *//* }
                        }
                    }*/
                }
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
fun Greeting(name: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {  }) {
                Text(text = name)
            }
        }
    }
}


@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
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

