package mosis.streetsandtotems.core.presentation

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import mosis.streetsandtotems.core.presentation.navigation.AppNavigation
import mosis.streetsandtotems.core.presentation.screens.TikiScreen
import mosis.streetsandtotems.ui.theme.AppTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissionsEnabled = object {
            var enabled = true
        }
        Log.d("tag", "aj")

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    Log.d("tag", "fine")
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    Log.d("tag", "coarse")

                }
                else -> {
                    Log.d("tag", "else")

                    permissionsEnabled.enabled = false
                    setContent {
                        AppTheme {
                            TikiScreen()
                        }
                    }
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )


        setContent {
            AppTheme {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
//                    if (permissionsEnabled.enabled)
                        AppNavigation(locationPermissionRequest)
//                    else TikiScreen()
                }
            }
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