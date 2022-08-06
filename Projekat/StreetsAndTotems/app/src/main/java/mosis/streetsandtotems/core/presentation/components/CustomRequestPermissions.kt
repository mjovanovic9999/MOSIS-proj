package mosis.streetsandtotems.core.presentation.components

import android.Manifest
import android.app.Activity
import android.content.Context
import android.location.LocationManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CustomRequestPermissions(
    context: Context,
    activity: Activity,
    permissions: List<String> = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_BACKGROUND_LOCATION
    )
) {
    val permissionsState = rememberMultiplePermissionsState(permissions = permissions)

    val lifecycleOwner = LocalLifecycleOwner.current

    val locationDialogOpenState = remember { mutableStateOf(false) }


    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    permissionsState.launchMultiplePermissionRequest()

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
    LocationDialog(
        activity = activity,
        locationDialogOpenState = locationDialogOpenState
    )
}