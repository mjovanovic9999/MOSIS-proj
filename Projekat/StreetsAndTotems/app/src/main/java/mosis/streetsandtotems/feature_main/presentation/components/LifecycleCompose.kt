package mosis.streetsandtotems.feature_main.presentation.components

import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import mosis.streetsandtotems.core.domain.util.LocationBroadcastReceiver
import mosis.streetsandtotems.core.presentation.utils.notification.NotificationProvider
import mosis.streetsandtotems.feature_settings_persistence.PreferencesDataStore
import mosis.streetsandtotems.services.LocationService

@Composable
fun LifecycleCompose(
    locationBroadcastReceiver: LocationBroadcastReceiver,
    notificationProvider: NotificationProvider
) {
    val context = LocalContext.current
    val backgroundServiceEnabled = remember { mutableStateOf(true) }

    val scope = rememberCoroutineScope()
    scope.launch {

        PreferencesDataStore(context).userSettingsFlow.map { it.runInBackground }.collect {
            backgroundServiceEnabled.value = it
        }
    }

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
                            if (backgroundServiceEnabled.value) {
                                notificationProvider.notifyDisable(false)
                            }


                        }

                    }
                    Lifecycle.Event.ON_PAUSE -> {
                        if (backgroundServiceEnabled.value && LocationService.isServiceStarted) {
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
