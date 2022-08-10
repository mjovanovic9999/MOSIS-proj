package mosis.streetsandtotems.feature_map.presentation.components

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import mosis.streetsandtotems.core.MessageConstants

@Composable
fun CustomRequestLocation(
    context: Context,
//    myLocation: MutableState<LocationDTO>
) {


    val locationDialogOpenState = remember { mutableStateOf(true) }


//    CustomLocationDialog(
//        activity = context.getActivity(),
//        locationDialogOpenState = locationDialogOpenState
//    )


    fun LocateMe(context: Context) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        val locationResult =
            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_LOW_POWER, null)

        locationResult.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val lastKnownLocation = task.result

                if (lastKnownLocation != null) {
/*                    Toast.makeText(this, lastKnownLocation.latitude.toString(), Toast.LENGTH_SHORT)
                        .show()
                    Toast.makeText(this, lastKnownLocation.longitude.toString(), Toast.LENGTH_SHORT)
                        .show()*/
//                    myLocation.value = LocationDTO(
//                        lastKnownLocation.latitude,
//                        lastKnownLocation.longitude,
//                        lastKnownLocation.accuracy
//                    )
                    Toast.makeText(
                        context,
                        lastKnownLocation.accuracy.toString() + "low",
                        Toast.LENGTH_SHORT
                    )

                } else {
                    Toast.makeText(context, "NULL", Toast.LENGTH_SHORT).show()

                }
            } else {
                Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()
            }

        }
    }
    LocateMe(context)

}

/*
val lifecycleOwner = LocalLifecycleOwner.current

Log.d("tag", locationDialogOpenState.value.toString() + "custom")

DisposableEffect(
key1 = lifecycleOwner,
effect = {
val observer = LifecycleEventObserver { _, event ->
if (event == Lifecycle.Event.ON_RESUME) {
//locationPermissionRequest.launch(permissions)
//TODO("SAMO OBRADITI KAD USER IDE DENY TJ ELSE")
Log.d("tag", "ON resume")


//                    val locationManager =
//                        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

//                    locationDialogOpenState.value =
//                        !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
//                                && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

//context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))

}
}
lifecycleOwner.lifecycle.addObserver(observer)

onDispose {
lifecycleOwner.lifecycle.removeObserver(observer)
}
}
)*/

@Composable
fun CustomRequestLocationDialog(
    activity: Activity,
    locationDialogOpenState: MutableState<Boolean>
) {
    if (locationDialogOpenState.value) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                Text(
                    MessageConstants.DIALOG_LOCATION_TITLE,
                )
            },
            text = {
                Text(
                    MessageConstants.DIALOG_LOCATION_TEXT,
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        locationDialogOpenState.value = false
                        activity.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                ) {
                    Text(MessageConstants.DIALOG_LOCATION_CONFIRM_BUTTON)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        activity.finishAndRemoveTask()
                    }
                ) {
                    Text(MessageConstants.DIALOG_LOCATION_DISMISS_BUTTON)
                }
            }
        )
    }
}