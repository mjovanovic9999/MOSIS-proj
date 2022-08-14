package mosis.streetsandtotems.core.presentation

import android.Manifest
import android.app.Service.STOP_FOREGROUND_REMOVE
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.core.app.ServiceCompat.stopForeground
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import mosis.streetsandtotems.core.presentation.navigation.AppNavigation
import mosis.streetsandtotems.core.presentation.utils.notification.BackgroundServicesEnabled
import mosis.streetsandtotems.core.presentation.utils.notification.NotificationProvider
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

    @Inject
    lateinit var networkManager: NetworkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        installSplashScreen().apply {}
        setContent {
            AppTheme {
                CustomRequestPermission(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                    ),
                )
                CustomRequestLocation(LocationService.isLocationEnabled)
                CustomRequestNetwork(NetworkManager.isNetworkConnectivityValid)
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
}







