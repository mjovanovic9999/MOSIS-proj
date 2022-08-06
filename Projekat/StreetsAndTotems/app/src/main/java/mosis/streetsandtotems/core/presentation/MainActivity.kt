package mosis.streetsandtotems.core.presentation

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import mosis.streetsandtotems.core.presentation.components.CustomRequestPermissions
import mosis.streetsandtotems.core.presentation.navigation.AppNavigation
import mosis.streetsandtotems.feature_map.presentation.MapViewModel
import mosis.streetsandtotems.feature_map.presentation.MapScreen
import mosis.streetsandtotems.ui.theme.AppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private val viewModel: MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

/*        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.LoadLocation()
        }


        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )//samo prvi put
        )*/

        locationEnable()


        setContent {
            AppTheme {


                /*AppNavigation()*/

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                    AppNavigation()
                }
            }
        }
    }

    fun locationEnable() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            Toast.makeText(this, "paok gps", Toast.LENGTH_SHORT)
                .show()
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Toast.makeText(this, "network", Toast.LENGTH_SHORT)
                .show()
        }

    }




        /*       Toast.makeText(this, viewModel.locationState.Latitude.toString(), Toast.LENGTH_SHORT)
               .show()
               Toast.makeText(this, viewModel.locationState.Longitude.toString(), Toast.LENGTH_SHORT)
                   .show()*/


        /*val perm = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )

        *//*    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
                Toast.makeText(this, "majok", Toast.LENGTH_SHORT)
                    .show()
            }*//*

*//*        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    Toast.makeText(this, "ok", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this, "nook", Toast.LENGTH_SHORT)
                        .show()
                }
            }*/


//        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//
//        val res =
//            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_LOW_POWER, null)
//
//        res.addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val lastKnownLocation = task.result
//
//                if (lastKnownLocation != null) {
//                    /*    LatLng(
//                        lastKnownLocation.latitude,
//                        lastKnownLocation.longitude
//                   )*/
///*                    Toast.makeText(this, lastKnownLocation.latitude.toString(), Toast.LENGTH_SHORT)
//                        .show()
//                    Toast.makeText(this, lastKnownLocation.longitude.toString(), Toast.LENGTH_SHORT)
//                        .show()*/
//                    Toast.makeText(
//                        this,
//                        lastKnownLocation.accuracy.toString() + "low",
//                        Toast.LENGTH_SHORT
//                    )
//
//                } else {
//                    Toast.makeText(this, "NULL", Toast.LENGTH_SHORT).show()
//
//                }
//            } else {
//                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
//            }
//
//        }


        /* if (ContextCompat.checkSelfPermission(
                 this.applicationContext,
                 Manifest.permission.ACCESS_FINE_LOCATION
             ) != PackageManager.PERMISSION_GRANTED
         )
             return

         val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
         val locationResult = fusedLocationProviderClient.lastLocation
         locationResult.addOnCompleteListener { task ->
             if (task.isSuccessful) {
                 val lastKnownLocation = task.result

                 if (lastKnownLocation != null) {
                     LatLng(
                         lastKnownLocation.latitude,
                         lastKnownLocation.longitude
                     )
                     Toast.makeText(this, lastKnownLocation.latitude.toString(), Toast.LENGTH_SHORT)
                         .show()
                     Toast.makeText(this, lastKnownLocation.longitude.toString(), Toast.LENGTH_SHORT)
                         .show()

                 } else {
                     Toast.makeText(this, "NULL", Toast.LENGTH_SHORT).show()

                 }
             } else {
                 Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
             }
         }*/


}