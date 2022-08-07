package mosis.streetsandtotems.core.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale
import dagger.hilt.android.AndroidEntryPoint
import mosis.streetsandtotems.core.presentation.navigation.AppNavigation
import mosis.streetsandtotems.core.presentation.screens.TikiScreen
import mosis.streetsandtotems.ui.theme.AppTheme

@ExperimentalPermissionsApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*      var permissionsEnabled = object { var enabled = true }



              val locationPermissionRequest = registerForActivityResult(
                  ActivityResultContracts.RequestMultiplePermissions()
              ) { permissions ->
                  when {
                      permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                      }
                      permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                      }
                      else -> {
                          permissionsEnabled.enabled=false
                      }
                  }
              }*/


        setContent {
            AppTheme {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {

                    val permissionsState = rememberMultiplePermissionsState(
                        permissions = listOf(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                        )
                    )

                    val lifecycleOwner = LocalLifecycleOwner.current
                    DisposableEffect(
                        key1 = lifecycleOwner,
                        effect = {
                            val observer = LifecycleEventObserver { _, event ->
                                if (event == Lifecycle.Event.ON_START) {
                                    permissionsState.launchMultiplePermissionRequest()
                                }
                            }
                            lifecycleOwner.lifecycle.addObserver(observer)

                            onDispose {
                                lifecycleOwner.lifecycle.removeObserver(observer)
                            }
                        }
                    )
                    permissionsState.permissions.forEach { perm ->
                        when (perm.permission) {
                            Manifest.permission.ACCESS_COARSE_LOCATION -> {
                                when {
                                    perm.status.isGranted -> {
                                        Text(text = "ACCESS_COARSE_LOCATION accepted")
                                    }
                                    perm.status.shouldShowRationale -> {
                                        Text(
                                            text = "ACCESS_COARSE_LOCATION ratioanle"
                                        )
                                    }
                                    else -> {
                                        Text(
                                            text = "ACCESS_COARSE_LOCATION was permanently"
                                        )
                                    }
                                }
                            }
                            Manifest.permission.ACCESS_FINE_LOCATION -> {
                                when {
                                    perm.status.isGranted -> {
                                        Text(text = "ACCESS_FINE_LOCATION permission accepted")
                                    }
                                    perm.status.shouldShowRationale -> {
                                        Text(
                                            text = "ACCESS_FINE_LOCATION rationale"
                                        )
                                    }
                                    else -> {
                                        Text(
                                            text = "ACCESS_FINE_LOCATION permission was permanently denied. "
                                        )
                                    }
                                }
                            }
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION -> {
                                when {
                                    perm.status.isGranted -> {
                                        Text(text = "ACCESS_BACKGROUND_LOCATION permission accepted")
                                    }
                                    perm.status.shouldShowRationale -> {
                                        Text(
                                            text = "ACCESS_BACKGROUND_LOCATION rationale"
                                        )
                                    }
                                    else -> {
                                        Text(
                                            text = "ACCESS_BACKGROUND_LOCATION was permanently denied. You can enable it in the app settings."
                                        )
                                    }
                                }
                            }
                        }
                    }

                    /* if(permissionsEnabled.enabled)
                         AppNavigation(locationPermissionRequest)
                     else TikiScreen()*/
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