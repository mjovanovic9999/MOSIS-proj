package mosis.streetsandtotems.feature_main.presentation.components

import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import mosis.streetsandtotems.core.domain.util.LocationBroadcastReceiver
import mosis.streetsandtotems.core.presentation.components.CustomButton
import mosis.streetsandtotems.core.presentation.utils.notification.BackgroundServicesEnabled
import mosis.streetsandtotems.core.presentation.utils.notification.NotificationProvider
import mosis.streetsandtotems.services.LocationService

@Composable
fun LifecycleCompose(
    locationBroadcastReceiver: LocationBroadcastReceiver,
    notificationProvider: NotificationProvider
) {


    val context = LocalContext.current

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_RESUME -> {

                        context.registerReceiver(
                            locationBroadcastReceiver,
                            IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
                        )
                        if (!LocationService.isServiceStarted) {
                            Log.d("tag", "startuje servis")

                            context.startForegroundService(
                                Intent(
                                    context,
                                    LocationService::class.java
                                )
                            )

                        } else {
                            if (BackgroundServicesEnabled.isEnabled) {
                                notificationProvider.notifyDisable(false)
                            }


                        }

                    }
                    Lifecycle.Event.ON_PAUSE -> {
                        if (BackgroundServicesEnabled.isEnabled && LocationService.isServiceStarted) {
                            notificationProvider.notifyDisable(true)
                        } else {
                            context.stopService(Intent(context, LocationService::class.java))
                        }

                        context.unregisterReceiver(
                            locationBroadcastReceiver,
                        )
                    }
                    else -> {}
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )
}
