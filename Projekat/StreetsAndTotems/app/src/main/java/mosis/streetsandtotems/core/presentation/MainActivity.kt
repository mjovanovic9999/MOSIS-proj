package mosis.streetsandtotems.core.presentation

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import mosis.streetsandtotems.core.domain.util.LocationBroadcastReceiver
import mosis.streetsandtotems.core.presentation.navigation.AppNavigation
import mosis.streetsandtotems.core.presentation.utils.notification.BackgroundServicesEnabled
import mosis.streetsandtotems.core.presentation.utils.notification.NotificationProvider
import mosis.streetsandtotems.core.presentation.utils.notification.NotificationBroadcastReceiver
import mosis.streetsandtotems.feature_map.presentation.components.CustomRequestLocation
import mosis.streetsandtotems.feature_map.presentation.components.CustomRequestNetwork
import mosis.streetsandtotems.feature_map.presentation.components.CustomRequestPermission
import mosis.streetsandtotems.services.LocationService
import mosis.streetsandtotems.services.NetworkManager
import mosis.streetsandtotems.ui.theme.AppTheme
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity() : ComponentActivity() {


    @Inject
    lateinit var notificationProvider: NotificationProvider

    val arePermissionsGranted = mutableStateOf(false)

    private val locationBroadcastReceiver = LocationBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {}
        setContent {
            AppTheme {

                if (!arePermissionsGranted.value) {
                    CustomRequestPermission(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                        ),arePermissionsGranted
                    )
                } else {
                    CustomRequestNetwork(NetworkManager.isNetworkConnectivityValid)
                    CustomRequestLocation(LocationService.isLocationEnabled)

                    AppNavigation()
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()

        registerReceiver(
            locationBroadcastReceiver,
            IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        )

        Log.d("tag", "onResumeActivity")


        if (!LocationService.isServiceStarted /*&& arePermissionsGranted.value*/)//treb si ostane ugasen i onova dugme
        {
            Log.d("tag", "startuje servis")

            startForegroundService(Intent(this, LocationService::class.java))

        } else {
            if (BackgroundServicesEnabled.isEnabled) {
                notificationProvider.notifyDisable(false)
            }


        }


    }

    override fun onPause() {

        if (BackgroundServicesEnabled.isEnabled && LocationService.isServiceStarted) {
            notificationProvider.notifyDisable(true)
        } else {
            stopService(Intent(this, LocationService::class.java))
        }

        unregisterReceiver(
            locationBroadcastReceiver,
        )

        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}







