package mosis.streetsandtotems.core.presentation.components

import android.Manifest
import android.content.Context
import android.location.LocationManager
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import mosis.streetsandtotems.core.domain.util.getActivity

@Composable
fun CustomRequestPermissions(
    context: Context,
    locationPermissionRequest: ActivityResultLauncher<Array<String>>,
    permissions: Array<String> = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION
    )
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val locationDialogOpenState = remember { mutableStateOf(false) }

    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    locationPermissionRequest.launch(permissions)

                    val locationManager =
                        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

                    locationDialogOpenState.value =
                        !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                                && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

                    //context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))

                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )
    CustomLocationDialog(
        activity = context.getActivity(),
        locationDialogOpenState = locationDialogOpenState
    )
}