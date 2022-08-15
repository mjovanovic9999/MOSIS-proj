package mosis.streetsandtotems.core.presentation

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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


    private val locationBroadcastReceiver = LocationBroadcastReceiver()
//    private val notificationBroadcastReceiver = NotificationBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerReceiver(
            locationBroadcastReceiver,
            IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        )

//        registerReceiver(
//            notificationBroadcastReceiver,
//            IntentFilter("android.location.PROVIDERS_CHANGED")
//        )


        installSplashScreen().apply {}
        setContent {
            AppTheme {
                CustomRequestPermission(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                    ),
                )
                CustomRequestNetwork(NetworkManager.isNetworkConnectivityValid)
                CustomRequestLocation(LocationService.isLocationEnabled)
                AppNavigation()
            }
        }

    }

    override fun onResume() {
        super.onResume()


        Log.d("tag", "onResumeActivity")


        if (!LocationService.isServiceStarted)//treb si ostane ugasen i onova dugme
        {
            startForegroundService(Intent(this, LocationService::class.java))
            Log.d("tag", "created")

        } else {
            if (BackgroundServicesEnabled.isEnabled) {
                notificationProvider.notifyDisable(false)
            }
        }

    }

    override fun onPause() {

        if (BackgroundServicesEnabled.isEnabled) {
            notificationProvider.notifyDisable(true)
        } else {
            stopService(Intent(this, LocationService::class.java))
        }



        super.onPause()
    }

    override fun onDestroy() {
        unregisterReceiver(
            locationBroadcastReceiver,
        )
//        unregisterReceiver(
//            notificationBroadcastReceiver,
//        )
        super.onDestroy()
    }
}







