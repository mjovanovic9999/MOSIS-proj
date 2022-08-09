package mosis.streetsandtotems.core.presentation

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import mosis.streetsandtotems.core.MessageConstants
import mosis.streetsandtotems.core.MessageConstants.DIALOG_PERMISSION_CONFIRM_BUTTON
import mosis.streetsandtotems.core.MessageConstants.DIALOG_PERMISSION_DISMISS_BUTTON
import mosis.streetsandtotems.core.MessageConstants.DIALOG_PERMISSION_TEXT
import mosis.streetsandtotems.core.MessageConstants.DIALOG_PERMISSION_TITLE
import mosis.streetsandtotems.core.presentation.navigation.AppNavigation
import mosis.streetsandtotems.ui.theme.AppTheme


@AndroidEntryPoint
class MainActivity() : ComponentActivity() {

    //private val mainActivityViewModel = MainActivityViewModel()
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    private var permissionState =  mutableStateOf(false)
    private lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("tag", "aj")


        startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {
                launchPermissionRequest()
            }


        locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    Log.d("tag", "fine")
                    permissionState.value = true

                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    Log.d("tag", "coarse")
                    permissionState.value = true

                }
                else -> {
                    Log.d("tag", "else")
                    permissionState.value = false

                }
            }
        }

        launchPermissionRequest()

        installSplashScreen().apply {
        }
        setContent {
            AppTheme {
                if (permissionState.value) {
                    AppNavigation()
                } else {
                    CustomDialogRequestPermissions()
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

    @Composable
    fun CustomDialogRequestPermissions() {

        AlertDialog(
            onDismissRequest = { },
            title = {
                Text(
                    MessageConstants.DIALOG_PERMISSION_TITLE,
                )
            },
            text = {
                Text(
                    MessageConstants.DIALOG_PERMISSION_TEXT,
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        val intent =
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri: Uri =
                            Uri.fromParts("package", packageName, null)
                        intent.data = uri

                        startForResult.launch(intent)
                    }
                ) {
                    Text(MessageConstants.DIALOG_PERMISSION_CONFIRM_BUTTON)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        finishAndRemoveTask()
                    }
                ) {
                    Text(MessageConstants.DIALOG_PERMISSION_DISMISS_BUTTON)
                }
            }
        )
    }

    private fun launchPermissionRequest(
        permissions: Array<String> = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            //Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
    ) {
        locationPermissionRequest.launch(
            permissions
        )
    }


}