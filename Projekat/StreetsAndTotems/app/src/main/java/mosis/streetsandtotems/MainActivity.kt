package mosis.streetsandtotems

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.compose.md_theme_dark_background
import mosis.streetsandtotems.ui.theme.AppTheme
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import ovh.plrapps.mapcompose.ui.MapUI
import org.osmdroid.config.Configuration.*

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
                    Greeting("Test")
                    MapLayout()
                    OnLifecycleEvent { owner, event ->
                        // do stuff on event
                        when (event) {
                            Lifecycle.Event.ON_RESUME -> {
                                Toast.makeText(this,"resume",Toast.LENGTH_SHORT).show()

                                }
                            else                      -> { /* other stuff */ }
                        }
                    }
                }
            }
        }

/*        val requestedepermisions=registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            isGranted:Boolean->
            if (isGranted) Log.i("AAAAAAAA","ok")
            else Log.i("AAAAAAAA","not ok")
        }*/
        reqPermission()

        this.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            Log.d("AAAAAAAAA",permissions.toString())
            permissions.entries.forEach {x->
                // check whether each permission is granted or not
                if(x.value==false) Log.i("nece", x.key)
                else Log.i("ocve",x.key+"KAO OCE")
            }
        }

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

            btn.setOnClickListener{Toast.makeText(context,"aaaaaaaa",Toast.LENGTH_SHORT).show()}


            getInstance().load(context, androidx.preference.PreferenceManager.getDefaultSharedPreferences(context));
            val map=view.findViewById<MapView>(R.id.map)

            map.setTileSource(TileSourceFactory.MAPNIK)

            val mapController = map.controller
            mapController.setZoom(16.0)// kao constat iz resources....
            val startPoint = GeoPoint( 43.321181, 21.895675)
            mapController.setCenter(startPoint)

            // do whatever you want...




            view // return the view
        },
        update = { btn ->
             }
    )

}
